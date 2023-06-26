package io.seventytwo.demo.model.order.entity;


import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    private Integer id;

    private int quantity;

    @ManyToOne
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
