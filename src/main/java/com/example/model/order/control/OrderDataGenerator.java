package com.example.model.order.control;

import com.example.model.order.control.CustomerRepository;
import com.example.model.order.control.ProductRepository;
import com.example.model.order.entity.Customer;
import com.example.model.order.entity.Order;
import com.example.model.order.entity.OrderItem;
import com.example.model.order.entity.Product;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@SpringComponent
public class OrderDataGenerator implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDataGenerator.class);

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderDataGenerator(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (productRepository.count() != 0L) {
            LOGGER.info("Using existing database");
            return;
        }

        LOGGER.info("Generating Customer and Order Data");

        LOGGER.info("... Generating 400 Sample Customer Entities with Orders ...");

        Product product = new Product();
        product.setName("Pencil");
        product.setPrice(2.5d);

        final Product finalProduct = productRepository.saveAndFlush(product);

        ExampleDataGenerator<Customer> customerDataGenerator = new ExampleDataGenerator<>(Customer.class, LocalDateTime.now());
        customerDataGenerator.setData(Customer::setFirstname, DataType.FIRST_NAME);
        customerDataGenerator.setData(Customer::setLastname, DataType.LAST_NAME);

        List<Customer> customers = customerDataGenerator.create(400, 123);
        customers.forEach(customer -> {
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
                    orderItems.get(0).setProduct(finalProduct);

                    orders.get(0).getItems().add(orderItems.get(0));
                }
            }

            customerRepository.saveAndFlush(customer);
        });

        LOGGER.info("Generated Customer and Order Data");
    }

}
