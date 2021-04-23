package com.example.application.model.order.boundry;

import com.example.application.model.order.entity.Customer;
import com.example.application.model.order.entity.Order;
import com.example.application.model.order.entity.OrderItem;
import com.example.application.model.order.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
public class OrderDataGenerator {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @GetMapping("/api/orders/generate")
    public void generateTestData() {
        Product product = new Product();
        product.setName("Pencil");
        product.setPrice(2.5d);
        em.persist(product);

        ExampleDataGenerator<Customer> customerDataGenerator = new ExampleDataGenerator<>(Customer.class, LocalDateTime.now());
        customerDataGenerator.setData(Customer::setFirstname, DataType.FIRST_NAME);
        customerDataGenerator.setData(Customer::setLastname, DataType.LAST_NAME);

        List<Customer> customers = customerDataGenerator.create(400, 123);
        customers.forEach(customer -> {
            em.persist(customer);

            Random random = new Random();
            for (int j = 0; j < random.nextInt(10) + 1; j++) {
                ExampleDataGenerator<Order> orderDataGenerator = new ExampleDataGenerator<>(Order.class, LocalDateTime.now());
                orderDataGenerator.setData(Order::setOrderDate, DataType.DATE_LAST_1_YEAR);
                List<Order> orders = orderDataGenerator.create(1, 124);
                customer.getOrders().add(orders.get(0));

                for (int k = 0; k < random.nextInt(20) + 1; k++) {
                    ExampleDataGenerator<OrderItem> orderItemDataGenerator = new ExampleDataGenerator<>(OrderItem.class, LocalDateTime.now());
                    orderItemDataGenerator.setData(OrderItem::setQuantity, DataType.NUMBER_UP_TO_10);
                    List<OrderItem> orderItems = orderItemDataGenerator.create(1, 123);
                    orderItems.get(0).setProduct(product);

                    orders.get(0).getItems().add(orderItems.get(0));
                }
            }
        });

    }
}
