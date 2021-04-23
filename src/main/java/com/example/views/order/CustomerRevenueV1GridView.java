package com.example.views.order;

import com.example.model.order.control.CustomerRepository;
import com.example.model.order.entity.Customer;
import com.example.views.main.MainView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;

@Route(value = "v1", layout = MainView.class)
@PageTitle("Customers Revenue (Version 1)")
public class CustomerRevenueV1GridView extends Div {

    public CustomerRevenueV1GridView(CustomerRepository customerRepository) {
        setHeightFull();

        Grid<Customer> grid = new Grid<>();
        grid.addColumn(Customer::getId).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(Customer::getFirstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(Customer::getLastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(Customer::getRevenue).setHeader("Revenue");

        grid.setItems(query -> customerRepository
                .findAll(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream()
        );

        grid.setHeightFull();

        add(grid);
    }
}
