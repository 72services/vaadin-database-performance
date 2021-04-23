package com.example.model.order.control;

import com.example.model.order.entity.CustomerInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findAllCustomersWithRevenue() {
        List<CustomerInfo> customers = customerRepository.findAllCustomersWithRevenue(PageRequest.of(1, 50));

        assertEquals(0, customers.size());
    }

    @Test
    void findAllCustomersWithRevenueSql() {
        List<CustomerInfo> customers = customerRepository.findAllCustomersWithRevenueSql(PageRequest.of(1, 50));

        assertEquals(0, customers.size());
    }
}
