package io.seventytwo.demo.api.order;

import io.seventytwo.demo.model.order.control.OrderRepository;
import io.seventytwo.demo.model.order.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderControllerV1 {

    private final OrderRepository orderRepository;

    public OrderControllerV1(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/api/v1/orders")
    public List<Order> getOrders(@RequestParam Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

}
