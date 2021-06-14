package io.seventytwo.demo.model.order.control;

import io.seventytwo.demo.model.order.entity.CustomerInfo;
import io.seventytwo.vaadinjooq.util.VaadinJooqUtil;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static io.seventytwo.demo.database.tables.Customer.CUSTOMER;
import static io.seventytwo.demo.database.tables.OrderItem.ORDER_ITEM;
import static io.seventytwo.demo.database.tables.Product.PRODUCT;
import static io.seventytwo.demo.database.tables.PurchaseOrder.PURCHASE_ORDER;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JooqTest {

    @Autowired
    private DSLContext dsl;

    @Test
    void findAllCustomersWithRevenue() {
        List<CustomerInfo> customers = dsl
                .select(CUSTOMER.ID, CUSTOMER.LASTNAME, CUSTOMER.FIRSTNAME, DSL.sum(PRODUCT.PRICE))
                .from(CUSTOMER)
                .join(PURCHASE_ORDER).on(PURCHASE_ORDER.CUSTOMER_ID.eq(CUSTOMER.ID))
                .join(ORDER_ITEM).on((ORDER_ITEM.ORDER_ID.eq(PURCHASE_ORDER.ID)))
                .join(PRODUCT).on((PRODUCT.ID.eq(ORDER_ITEM.PRODUCT_ID)))
                .groupBy(CUSTOMER.ID)
                .offset(1)
                .limit(50)
                .fetchInto(CustomerInfo.class);

        assertThat(customers.size()).isEqualTo(50);
    }
}
