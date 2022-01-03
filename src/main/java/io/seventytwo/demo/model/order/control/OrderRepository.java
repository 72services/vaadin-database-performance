package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.model.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select c.orders from Customer c where c.id = :customerId")
    List<Order> findByCustomerId(Integer customerId);

    @Query(value = """
            select cast(pos as varchar)
                from (select json_agg(po) as orders
                     from (select p.id,
                                  p.order_date,
                                  (select json_agg(oi)
                                   from (select i.id, i.quantity, pr.name as product
                                         from order_item as i
                                                  join product pr on pr.id = i.product_id
                                         where i.order_id = p.id
                                        ) oi
                                  ) as items
                           from purchase_order as p
                           where p.customer_id = ?) po) pos
            """, nativeQuery = true)
    String findByCustomerIdAsJson(Integer customerId);
}
