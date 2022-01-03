package io.seventytwo.demo.views.home;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.seventytwo.demo.views.layout.ApplicationLayout;

@Route(value = "", layout = ApplicationLayout.class)
@PageTitle("Home")
public class HomeView extends VerticalLayout {

    public HomeView() {
        setHeightFull();

        add(new H1("High-performance data access with Vaadin"));
    }
}
