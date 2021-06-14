package io.seventytwo.demo.views.order;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.model.order.entity.CustomerInfo;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import io.seventytwo.vaadinjooq.util.VaadinJooqUtil;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static io.seventytwo.demo.database.tables.Customer.CUSTOMER;
import static io.seventytwo.demo.database.tables.OrderItem.ORDER_ITEM;
import static io.seventytwo.demo.database.tables.Product.PRODUCT;
import static io.seventytwo.demo.database.tables.PurchaseOrder.PURCHASE_ORDER;

@Route(value = "v3", layout = ApplicationLayout.class)
@PageTitle("Customers Revenue (Version 3)")
public class CustomerRevenueV3GridView extends Div {

    public CustomerRevenueV3GridView(DSLContext dsl) {
        setHeightFull();

        var grid = new Grid<CustomerInfo>();
        grid.addColumn(CustomerInfo::id).setHeader("ID").setSortable(true).setSortProperty(CUSTOMER.ID.getName());
        grid.addColumn(CustomerInfo::firstname).setHeader("First Name").setSortable(true).setSortProperty(CUSTOMER.FIRSTNAME.getName());
        grid.addColumn(CustomerInfo::lastname).setHeader("Last Name").setSortable(true).setSortProperty(CUSTOMER.LASTNAME.getName());
        grid.addColumn(CustomerInfo::revenue).setHeader("Revenue");

        grid.setItems(
                query -> dsl
                        .select(CUSTOMER.ID, CUSTOMER.LASTNAME, CUSTOMER.FIRSTNAME, DSL.sum(PRODUCT.PRICE))
                        .from(CUSTOMER)
                        .join(PURCHASE_ORDER).on(PURCHASE_ORDER.CUSTOMER_ID.eq(CUSTOMER.ID))
                        .join(ORDER_ITEM).on((ORDER_ITEM.ORDER_ID.eq(PURCHASE_ORDER.ID)))
                        .join(PRODUCT).on((PRODUCT.ID.eq(ORDER_ITEM.PRODUCT_ID)))
                        .groupBy(CUSTOMER.ID)
                        .orderBy(VaadinJooqUtil.orderFields(CUSTOMER, query))
                        .offset(query.getOffset())
                        .limit(query.getLimit())
                        .fetchStreamInto(CustomerInfo.class)
        );

        grid.setHeightFull();

        add(grid);
    }
}
