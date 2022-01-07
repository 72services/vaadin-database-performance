package io.seventytwo.demo.views.order;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.order.control.CustomerRepository;
import io.seventytwo.demo.order.entity.CustomerInfo;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import org.springframework.data.domain.PageRequest;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@Route(value = "customer-revenue-jpa-records", layout = ApplicationLayout.class)
@PageTitle("Customer Revenue JPA Records")
public class CustomerRevenueV20JpaRecordView extends VerticalLayout {

    private final CustomerRepository customerRepository;
    private final Grid<CustomerInfo> grid;

    public CustomerRevenueV20JpaRecordView(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

        setHeightFull();

        TextField filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(event -> loadData(event.getValue()));
        filter.setPlaceholder("Search");

        add(filter);

        grid = new Grid<>();
        grid.addColumn(CustomerInfo::id).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(CustomerInfo::firstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(CustomerInfo::lastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(CustomerInfo::revenue).setHeader("Revenue");

        loadData("");

        grid.setHeightFull();

        add(grid);
    }

    private void loadData(String name) {
        grid.setItems(
                query -> customerRepository.findAllCustomersWithRevenue(
                        PageRequest.of(query.getPage(), query.getPageSize(), toSpringDataSort(query)), name).stream(),
                query -> customerRepository.countCustomersWithRevenue(name)
        );
    }
}
