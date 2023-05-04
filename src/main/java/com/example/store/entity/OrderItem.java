package com.example.store.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order_items")
    private Long id_order_items;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_good")
    private Good good;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Double total_price;

    public OrderItem() {
    }

    public OrderItem(Order order, Good good, Integer quantity, Double total_price) {
        this.order = order;
        this.good = good;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public Long getId_order_items() {
        return id_order_items;
    }

    public void setId_order_items(Long id_order_items) {
        this.id_order_items = id_order_items;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}