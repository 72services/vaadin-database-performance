package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.model.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select c.orders from Customer c where c.id = :customerId")
    List<Order> findByCustomerId(Integer customerId);
}
