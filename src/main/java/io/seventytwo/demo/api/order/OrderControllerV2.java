package io.seventytwo.demo.api.order;

import org.jooq.DSLContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV2 {

    private final DSLContext dslContext;

    public OrderControllerV2(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @GetMapping(value = "/api/v2/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrdersAsJson(@RequestParam Integer customerId) {
        return dslContext.fetchOne("""
                select json_agg(po) as orders
                from (select p.id,
                             p.order_date,
                             (select json_agg(oi)
                              from (select i.id, i.quantity, pr.name as product
                                    from order_item as i
                                    join product pr on pr.id = i.product_id
                                    where i.order_id = p.id
                                   ) oi
                             ) as items
                      from purchase_order as p where p.customer_id = ?) po
                """, customerId).into(String.class);
    }

    @GetMapping(value = "/api/v2/orders", produces = MediaType.APPLICATION_XML_VALUE)
    public String getOrdersAsXml(@RequestParam Integer customerId) {
        return dslContext.fetchOne("""
                  select xmlelement(name "orders",
                                    xmlagg(xmlelement(name "order",
                                                      xmlattributes(p.id, p.order_date),
                                                      xmlelement(name "items",
                                                                (select xmlagg(xmlelement(name "item",
                                                                               xmlattributes(i.id, i.quantity, pr.name)))
                                                                 from order_item i
                                                                 join product pr on i.product_id = pr.id
                                                                 where i.order_id = p.id)
                                                              )
                                                    )
                                        )
                                 )
                from purchase_order p
                where p.customer_id = ?
                """, customerId).into(String.class);
    }
}
