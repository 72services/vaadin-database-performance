package io.seventytwo.demo.views.employee;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import io.seventytwo.demo.model.employee.control.EmployeeService;
import io.seventytwo.demo.model.employee.entity.Employee;
import io.seventytwo.demo.views.layout.ApplicationLayout;
import org.springframework.data.domain.PageRequest;

@SuppressWarnings("FieldCanBeLocal")
@Route(value = "master-detail/:sampleEmployeeID?/:action?(edit)", layout = ApplicationLayout.class)
@PageTitle("Employee Master-Detail")
public class EmployeeMasterDetailView extends VerticalLayout implements BeforeEnterObserver {

    private final String SAMPLE_EMPLOYEE_ID = "sampleEmployeeID";
    private final String SAMPLE_EMPLOYEE_EDIT_ROUTE_TEMPLATE = "master-detail/%d/edit";

    private final Grid<Employee> grid = new Grid<>(Employee.class, false);

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phone;
    private DatePicker dateOfBirth;
    private TextField occupation;
    private Checkbox important;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Employee> binder;

    private Employee employee;

    private final EmployeeService employeeService;

    public EmployeeMasterDetailView(EmployeeService employeeService) {
        this.employeeService = employeeService;

        addClassName("master-detail-view");

        // Create UI
        var splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("firstName").setAutoWidth(true);
        grid.addColumn("lastName").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("phone").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);
        grid.addColumn("occupation").setAutoWidth(true);
        TemplateRenderer<Employee> importantRenderer = TemplateRenderer.<Employee>of("""
                        <vaadin-icon hidden='[[!item.important]]' icon='vaadin:check' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-primary-text-color);'></vaadin-icon>
                        <vaadin-icon hidden='[[item.important]]' icon='vaadin:minus' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: var(--lumo-disabled-text-color);'></vaadin-icon>
                        """)
                .withProperty("important", Employee::isImportant);
        grid.addColumn(importantRenderer).setHeader("Important").setAutoWidth(true);

        grid.setItems(query -> employeeService.list(
                        PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(SAMPLE_EMPLOYEE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(EmployeeMasterDetailView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Employee.class);

        // Bind fields. This where you'd define e.g. validation rules

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.employee == null) {
                    this.employee = new Employee();
                }
                binder.writeBean(this.employee);

                employeeService.update(this.employee);
                clearForm();
                refreshGrid();
                Notification.show("SampleEmployee details stored.");
                UI.getCurrent().navigate(EmployeeMasterDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the sampleEmployee details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        var sampleEmployeeId = event.getRouteParameters().getInteger(SAMPLE_EMPLOYEE_ID);
        if (sampleEmployeeId.isPresent()) {
            var sampleEmployeeFromBackend = employeeService.get(sampleEmployeeId.get());
            if (sampleEmployeeFromBackend.isPresent()) {
                populateForm(sampleEmployeeFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested sampleEmployee was not found, ID = %d", sampleEmployeeId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available, refresh grid
                refreshGrid();
                event.forwardTo(EmployeeMasterDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        var editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        var editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        var formLayout = new FormLayout();
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        email = new TextField("Email");
        phone = new TextField("Phone");
        dateOfBirth = new DatePicker("Date Of Birth");
        occupation = new TextField("Occupation");
        important = new Checkbox("Important");
        important.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[]{firstName, lastName, email, phone, dateOfBirth, occupation, important};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        var buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        var wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Employee value) {
        this.employee = value;
        binder.readBean(this.employee);
    }
}
