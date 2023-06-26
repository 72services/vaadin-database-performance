package io.seventytwo.demo.model.order.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    private Integer id;

    private String firstname;
    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("orderDate DESC")
    @JoinColumn(name = "customer_id")
    private List<Order> orders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getRevenue() {
        return orders.stream().mapToDouble(Order::getTotalAmount).sum();
    }

}
