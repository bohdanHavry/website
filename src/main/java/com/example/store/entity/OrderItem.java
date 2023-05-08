package com.example.store.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_order_items")
    private Long id_order_items;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_good", nullable = false)
    private Good good;

    @Column(name = "quantity")
    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(Good good, Integer quantity) {
        this.good = good;
        this.quantity = quantity;
    }

    public Long getId_order_items() {
        return id_order_items;
    }

    public void setId_order_items(Long id_order_items) {
        this.id_order_items = id_order_items;
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
}