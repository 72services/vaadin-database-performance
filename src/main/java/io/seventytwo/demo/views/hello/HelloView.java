package io.seventytwo.demo.views.hello;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.model.employee.control.EmployeeRepository;
import io.seventytwo.demo.model.employee.entity.Employee;
import io.seventytwo.demo.views.layout.ApplicationLayout;

import java.util.stream.Stream;

@Route(value = "", layout = ApplicationLayout.class)
@PageTitle("Vaadin Dev Day")
public class HelloView extends VerticalLayout {

    public HelloView() {
        setHeightFull();

        add(new H1("High-performance data access with Vaadin"));
    }
}
