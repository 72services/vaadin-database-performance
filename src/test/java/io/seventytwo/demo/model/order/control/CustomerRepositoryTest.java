package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.model.order.entity.CustomerInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findAllCustomersWithRevenue() {
        List<CustomerInfo> customers = customerRepository.findAllCustomersWithRevenue(PageRequest.of(1, 50), "");

        assertThat(customers.size()).isEqualTo(50);
    }
}
