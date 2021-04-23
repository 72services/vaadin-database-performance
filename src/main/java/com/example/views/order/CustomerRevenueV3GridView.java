package com.example.views.order;

import com.example.model.order.control.CustomerRepository;
import com.example.model.order.entity.CustomerInfo;
import com.example.views.main.MainView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;

@Route(value = "v3", layout = MainView.class)
@PageTitle("Customers Revenue (Version 3)")
public class CustomerRevenueV3GridView extends Div {

    public CustomerRevenueV3GridView(CustomerRepository customerRepository) {
        setHeightFull();

        Grid<CustomerInfo> grid = new Grid<>();
        grid.addColumn(CustomerInfo::id).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(CustomerInfo::firstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(CustomerInfo::lastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(CustomerInfo::revenue).setHeader("Revenue");

        grid.setItems(query -> customerRepository
                .findAllCustomersWithRevenueSql(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream()
        );

        grid.setHeightFull();

        add(grid);
    }
}
