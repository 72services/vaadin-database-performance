package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.model.order.entity.Customer;
import io.seventytwo.demo.model.order.entity.CustomerInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            select new io.seventytwo.demo.model.order.entity.CustomerInfo(c.id, c.lastname, c.firstname, sum(i.product.price))
            from Customer c join c.orders o join o.items i
            where lower(c.firstname) like :name% or lower(c.lastname) like :name%
            group by c.id
            """)
    List<CustomerInfo> findAllCustomersWithRevenue(Pageable pageable, String name);

    @Query("select c from Customer c where lower(c.firstname) like :name% or lower(c.lastname) like :name%")
    List<Customer> findAllByLastnameLikeOrFirstnameLike(Pageable pageable, String name);

    @Query("select count(c) from Customer c where lower(c.firstname) like :name% or lower(c.lastname) like :name%")
    int countAllByLastnameLikeOrFirstnameLike(String name);
}
