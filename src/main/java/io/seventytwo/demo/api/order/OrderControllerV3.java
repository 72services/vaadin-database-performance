package io.seventytwo.demo.api.order;

import org.jooq.DSLContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.seventytwo.demo.database.tables.OrderItem.ORDER_ITEM;
import static io.seventytwo.demo.database.tables.Product.PRODUCT;
import static io.seventytwo.demo.database.tables.PurchaseOrder.PURCHASE_ORDER;
import static org.jooq.Records.mapping;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

@RestController
public class OrderControllerV3 {

    private final DSLContext dslContext;

    public OrderControllerV3(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @GetMapping(value = "/api/v3/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OrdersDTO.OrderDTO> getOrders(@RequestParam Integer customerId) {
        return dslContext
                .select(PURCHASE_ORDER.ID,
                        PURCHASE_ORDER.ORDER_DATE,
                        multiset(
                                select(ORDER_ITEM.ID, ORDER_ITEM.QUANTITY, PRODUCT.NAME)
                                        .from(ORDER_ITEM)
                                        .join(PRODUCT).on(PRODUCT.ID.eq(ORDER_ITEM.PRODUCT_ID))
                                        .where(ORDER_ITEM.ORDER_ID.eq(PURCHASE_ORDER.ID))
                        ).convertFrom(r -> r.into(OrdersDTO.OrderDTO.OrderItemDTO.class))
                )
                .from(PURCHASE_ORDER)
                .where(PURCHASE_ORDER.CUSTOMER_ID.eq(customerId))
                .fetch(mapping(OrdersDTO.OrderDTO::new));
    }

    @GetMapping(value = "/api/v3/orders", produces = MediaType.APPLICATION_XML_VALUE)
    public OrdersDTO getOrdersAsXml(@RequestParam Integer customerId) {
        return new OrdersDTO(getOrders(customerId));
    }

}
