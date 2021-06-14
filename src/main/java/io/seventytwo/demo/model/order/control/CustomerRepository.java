package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.model.order.entity.Customer;
import io.seventytwo.demo.model.order.entity.CustomerInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByLastnameLikeOrFirstnameLike(Pageable pageable, String lastname, String firstname);

    @Query("""
            SELECT NEW io.seventytwo.demo.model.order.entity.CustomerInfo(c.id, c.lastname, c.firstname, SUM(i.product.price))
            FROM Customer c JOIN c.orders o JOIN o.items i
            WHERE lower(c.firstname) like :name% or lower(c.lastname) like :name% 
            GROUP BY c.id
            """)
    List<CustomerInfo> findAllCustomersWithRevenue(Pageable pageable, String name);
}
