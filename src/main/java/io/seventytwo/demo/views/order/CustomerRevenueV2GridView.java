package io.seventytwo.demo.views.order;

import io.seventytwo.demo.model.order.control.CustomerRepository;
import io.seventytwo.demo.model.order.entity.CustomerInfo;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.PageRequest;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@Route(value = "v2", layout = ApplicationLayout.class)
@PageTitle("Customers Revenue (Version 2)")
public class CustomerRevenueV2GridView extends Div {

    public CustomerRevenueV2GridView(CustomerRepository customerRepository) {
        setHeightFull();

        var grid = new Grid<CustomerInfo>();
        grid.addColumn(CustomerInfo::id).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(CustomerInfo::firstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(CustomerInfo::lastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(CustomerInfo::revenue).setHeader("Revenue");

        grid.setItems(
                query -> customerRepository
                        .findAllCustomersWithRevenue(PageRequest.of(query.getPage(), query.getPageSize(), toSpringDataSort(query)))
                        .stream()
        );

        grid.setHeightFull();

        add(grid);
    }
}
