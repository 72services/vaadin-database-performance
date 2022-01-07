package io.seventytwo.demo.order.control;

import io.seventytwo.demo.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select c.orders from Customer c where c.id = :customerId")
    List<Order> findByCustomerId(Integer customerId);
}
