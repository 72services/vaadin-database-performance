package io.seventytwo.demo.views.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.seventytwo.demo.views.employee.EmployeeMasterDetailView;
import io.seventytwo.demo.views.employee.EmployeeTreeView;
import io.seventytwo.demo.views.order.*;

/**
 * The main view is a top-level placeholder for other views.
 */
public class ApplicationLayout extends AppLayout {
    private H1 viewTitle;

    public ApplicationLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("My App");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.SMALL);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Customer Revenue List", CustomerRevenueV10JpaView.class),
                new SideNavItem("Customer Revenue Paging", CustomerRevenueV11JpaPagingView.class),
                new SideNavItem("Customer Revenue Count", CustomerRevenueV12JpaCountView.class),
                new SideNavItem("Customer Revenue Estimate", CustomerRevenueV13JpaEstimateView.class),
                new SideNavItem("Customer Revenue Records", CustomerRevenueV20JpaRecordView.class),
                new SideNavItem("Customer Revenue Filter", CustomerRevenueV30LegacyFilterView.class),
                new SideNavItem("Customer Revenue jOOQ", CustomerRevenueV40JooqView.class),
                new SideNavItem("Employee Grid", EmployeeMasterDetailView.class),
                new SideNavItem("Employee Tree", EmployeeTreeView.class));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
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
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
