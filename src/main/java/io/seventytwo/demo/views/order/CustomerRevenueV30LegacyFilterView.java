package io.seventytwo.demo.views.order;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.order.control.CustomerRepository;
import io.seventytwo.demo.order.entity.CustomerInfo;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import org.springframework.data.domain.PageRequest;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@Route(value = "customer-revenue-jpa-filter-data-provider", layout = ApplicationLayout.class)
@PageTitle("Customer Revenue with Configurable Filter")
public class CustomerRevenueV30LegacyFilterView extends VerticalLayout {

    private final ConfigurableFilterDataProvider<CustomerInfo, Void, String> dataProvider;

    public CustomerRevenueV30LegacyFilterView(CustomerRepository customerRepository) {
        setHeightFull();

        CallbackDataProvider<CustomerInfo, String> callbackDataProvider = DataProvider.fromFilteringCallbacks(
                query -> customerRepository.findAllCustomersWithRevenue(
                        PageRequest.of(query.getPage(), query.getPageSize(), toSpringDataSort(query)), query.getFilter().orElse("")).stream(),
                query -> customerRepository.countCustomersWithRevenue(query.getFilter().orElse("")));

        dataProvider = callbackDataProvider.withConfigurableFilter();

        var filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.setPlaceholder("Search");

        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        add(filter);

        var grid = new Grid<CustomerInfo>();
        grid.setHeightFull();

        grid.addColumn(CustomerInfo::id).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(CustomerInfo::firstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(CustomerInfo::lastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(CustomerInfo::revenue).setHeader("Revenue");

        grid.setItems(dataProvider);

        add(grid);
    }

}
