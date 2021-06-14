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
import io.seventytwo.demo.model.order.control.CustomerRepository;
import io.seventytwo.demo.model.order.entity.Customer;
import io.seventytwo.demo.model.order.entity.CustomerInfo;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import org.springframework.data.domain.PageRequest;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringDataSort;

@Route(value = "v4", layout = ApplicationLayout.class)
@PageTitle("Customers Revenue (Version 4)")
public class CustomerRevenueV4GridView extends VerticalLayout {

    private final ConfigurableFilterDataProvider<CustomerInfo, Void, String> dataProvider;

    public CustomerRevenueV4GridView(CustomerRepository customerRepository) {
        setHeightFull();

        CallbackDataProvider<CustomerInfo, String> callbackDataProvider = DataProvider.fromFilteringCallbacks(
                query -> customerRepository.findAllCustomersWithRevenue(
                        PageRequest.of(query.getPage(), query.getPageSize(), toSpringDataSort(query)), query.getFilter().orElse("")).stream(),
                query -> customerRepository.countAllByLastnameLikeOrFirstnameLike(query.getFilter().orElse("")));

        dataProvider = callbackDataProvider.withConfigurableFilter();

        var filter = new TextField();
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.setPlaceholder("Search");

        filter.addValueChangeListener(event -> {
            dataProvider.setFilter(event.getValue());
            dataProvider.refreshAll();
        });

        add(filter);

        var grid = new Grid<CustomerInfo>();
        grid.setHeightFull();

        grid.addColumn(CustomerInfo::id).setHeader("ID").setSortable(true).setSortProperty("id");
        grid.addColumn(CustomerInfo::firstname).setHeader("First Name").setSortable(true).setSortProperty("firstname");
        grid.addColumn(CustomerInfo::lastname).setHeader("Last Name").setSortable(true).setSortProperty("lastname");
        grid.addColumn(CustomerInfo::revenue).setHeader("Revenue");

        grid.setDataProvider(dataProvider);

        add(grid);
    }

}
