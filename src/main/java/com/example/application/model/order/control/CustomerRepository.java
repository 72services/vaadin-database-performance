package com.example.application.model.order.control;

import com.example.application.model.order.entity.Customer;
import com.example.application.model.order.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            SELECT NEW com.example.application.model.order.entity.CustomerInfo(c.id, c.lastname, c.firstname, SUM(i.product.price))
            FROM Customer c JOIN c.orders o JOIN o.items i
            WHERE lower(c.lastname) LIKE lower(:term)
            GROUP BY c.id, c.lastname, c.firstname
            ORDER BY c.lastname, c.firstname
            """)
    List<CustomerInfo> findAllCustomersWithRevenue();
}
