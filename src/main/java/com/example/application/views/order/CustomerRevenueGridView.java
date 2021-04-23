package com.example.application.views.order;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "revenue", layout = MainView.class)
@PageTitle("Master-Detail")
public class CustomerRevenueGridView extends Div {
}
