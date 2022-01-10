package io.seventytwo.demo.views.order;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridLazyDataView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.model.order.control.CustomerRepository;
import io.seventytwo.demo.model.order.entity.Customer;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import org.springframework.data.domain.PageRequest;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@Route(value = "customer-revenue-jpa-estimate", layout = ApplicationLayout.class)
@PageTitle("Customer Revenue JPA Count")
public class CustomerRevenueV13JpaEstimateView extends VerticalLayout {

    public CustomerRevenueV13JpaEstimateView(CustomerRepository customerRepository) {
        setHeightFull();

        Grid<Customer> grid = new Grid<>();
        grid.setHeightFull();

        grid.addColumn(Customer::getId).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(Customer::getFirstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(Customer::getLastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(Customer::getRevenue).setHeader("Revenue");

        GridLazyDataView<Customer> dataView = grid.setItems(
                query -> customerRepository.findAll(PageRequest.of(query.getPage(), query.getPageSize(), toSpringDataSort(query))).stream());

        dataView.setItemCountEstimate(400);

        add(grid);
    }

}
