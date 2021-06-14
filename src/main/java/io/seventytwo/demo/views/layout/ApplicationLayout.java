package io.seventytwo.demo.views.layout;

import io.seventytwo.demo.views.employee.EmployeeTreeView;
import io.seventytwo.demo.views.order.CustomerRevenueEntityGridView;
import io.seventytwo.demo.views.order.CustomerRevenueDtoGridView;
import io.seventytwo.demo.views.employee.EmployeeMasterDetailView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import io.seventytwo.demo.views.order.CustomerRevenueJooqGridView;
import io.seventytwo.demo.views.order.CustomerRevenueFilteringGridView;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class ApplicationLayout extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;

    public ApplicationLayout() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        var layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(new Avatar());
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        var logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "My App logo"));
        logoLayout.add(new H1("My App"));

        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        var tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{
                createTab("Employees", EmployeeMasterDetailView.class),
                createTab("Employee Tree", EmployeeTreeView.class),
                createTab("Customers Revenue with Entities", CustomerRevenueEntityGridView.class),
                createTab("Customers Revenue with DTOs", CustomerRevenueDtoGridView.class),
                createTab("Customers Revenue with jOOQ", CustomerRevenueJooqGridView.class),
                createTab("Customers Revenue with Filtering", CustomerRevenueFilteringGridView.class)
        };
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        var tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        var title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
