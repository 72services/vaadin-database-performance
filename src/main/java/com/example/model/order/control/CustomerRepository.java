package com.example.model.order.control;

import com.example.model.order.entity.Customer;
import com.example.model.order.entity.CustomerInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            SELECT NEW com.example.model.order.entity.CustomerInfo(c.id, c.lastname, c.firstname, SUM(i.product.price))
            FROM Customer c JOIN c.orders o JOIN o.items i
            GROUP BY c.id
            """)
    List<CustomerInfo> findAllCustomersWithRevenue(Pageable pageable);
}
