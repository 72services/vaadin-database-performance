package io.seventytwo.demo.views.order;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.model.order.control.CustomerRepository;
import io.seventytwo.demo.model.order.entity.Customer;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import org.springframework.data.domain.PageRequest;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@Route(value = "v1", layout = ApplicationLayout.class)
@PageTitle("Customers Revenue with Entities")
public class CustomerRevenueEntityGridView extends VerticalLayout {

    private final CustomerRepository customerRepository;
    private final Grid<Customer> grid;
    private final TextField filter;

    public CustomerRevenueEntityGridView(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

        setHeightFull();

        filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(event -> loadData());
        filter.setPlaceholder("Search");

        add(filter);

        grid = new Grid<>();
        grid.addColumn(Customer::getId).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(Customer::getFirstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(Customer::getLastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(Customer::getRevenue).setHeader("Revenue");

        loadData();

        grid.setHeightFull();

        add(grid);
    }

    private void loadData() {
        grid.setItems(
                query -> customerRepository.findAllByLastnameLikeOrFirstnameLike(
                        PageRequest.of(query.getPage(), query.getPageSize(), toSpringDataSort(query)),
                        filter.getValue() + "%").stream()
        );
    }

}
