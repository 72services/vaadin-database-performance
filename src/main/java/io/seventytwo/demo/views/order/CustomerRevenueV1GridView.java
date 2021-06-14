package io.seventytwo.demo.views.order;

import io.seventytwo.demo.model.order.control.CustomerRepository;
import io.seventytwo.demo.model.order.entity.Customer;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.PageRequest;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@Route(value = "v1", layout = ApplicationLayout.class)
@PageTitle("Customers Revenue (Version 1)")
public class CustomerRevenueV1GridView extends Div {

    public CustomerRevenueV1GridView(CustomerRepository customerRepository) {
        setHeightFull();

        var grid = new Grid<Customer>();
        grid.addColumn(Customer::getId).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(Customer::getFirstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(Customer::getLastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(Customer::getRevenue).setHeader("Revenue");

        grid.setItems(
                query -> customerRepository
                        .findAll(PageRequest.of(query.getPage(), query.getPageSize(), toSpringDataSort(query)))
                        .stream(),
                query -> (int) customerRepository.count()
        );

        grid.setHeightFull();

        add(grid);
    }
}
