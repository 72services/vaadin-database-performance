package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.order.control.CustomerRepository;
import io.seventytwo.demo.order.entity.CustomerInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findAllCustomersWithRevenue() {
        List<CustomerInfo> customers = customerRepository.findAllCustomersWithRevenue(PageRequest.of(1, 50), "");

        assertThat(customers.size()).isEqualTo(50);
    }
}
