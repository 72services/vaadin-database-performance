package io.seventytwo.demo.views.layout;

import io.seventytwo.demo.views.employee.EmployeeTreeView;
import io.seventytwo.demo.views.home.HomeView;
import io.seventytwo.demo.views.order.CustomerRevenueV10JpaView;
import io.seventytwo.demo.views.order.CustomerRevenueV11JpaPagingView;
import io.seventytwo.demo.views.order.CustomerRevenueV12JpaCountView;
import io.seventytwo.demo.views.order.CustomerRevenueV13JpaEstimateView;
import io.seventytwo.demo.views.order.CustomerRevenueV20JpaRecordView;
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
import io.seventytwo.demo.views.order.CustomerRevenueV40JooqView;
import io.seventytwo.demo.views.order.CustomerRevenueV30LegacyFilterView;

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
        logoLayout.add(new Image("images/logo.png", "Logo"));
        logoLayout.add(new H1("ERP"));

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
                createTab("Home", HomeView.class),
                createTab("Customer Revenue List", CustomerRevenueV10JpaView.class),
                createTab("Customer Revenue Paging", CustomerRevenueV11JpaPagingView.class),
                createTab("Customer Revenue Count", CustomerRevenueV12JpaCountView.class),
                createTab("Customer Revenue Estimate", CustomerRevenueV13JpaEstimateView.class),
                createTab("Customer Revenue Records", CustomerRevenueV20JpaRecordView.class),
                createTab("Customer Revenue Filter", CustomerRevenueV30LegacyFilterView.class),
                createTab("Customer Revenue jOOQ", CustomerRevenueV40JooqView.class),
                createTab("Employee Grid", EmployeeMasterDetailView.class),
                createTab("Employee Tree", EmployeeTreeView.class)
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
