package io.seventytwo.demo.views.order;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.model.order.control.CustomerRepository;
import io.seventytwo.demo.model.order.entity.Customer;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import org.springframework.data.domain.Sort;

@Route(value = "customer-revenue-jpa-list", layout = ApplicationLayout.class)
@PageTitle("Customer Revenue JPA List")
public class CustomerRevenueV10JpaView extends VerticalLayout {

    public CustomerRevenueV10JpaView(CustomerRepository customerRepository) {
        setHeightFull();

        Grid<Customer> grid = new Grid<>();
        grid.setHeightFull();

        grid.addColumn(Customer::getId).setHeader("ID").setSortable(true);
        grid.addColumn(Customer::getFirstname).setHeader("First Name").setSortable(true);
        grid.addColumn(Customer::getLastname).setHeader("Last Name").setSortable(true);
        grid.addColumn(Customer::getRevenue).setHeader("Revenue").setSortable(true);

        grid.setItems(customerRepository.findAll(Sort.by("firstname", "lastname")));

        add(grid);
    }

}
