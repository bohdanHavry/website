package com.example.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
@Data
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cart")
    private Integer id_cart;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Transient
    @Column(name = "totalPrice")
    private Double totalPrice;

    @Transient
    @Column(name = "itemsNumber")
    private int itemsNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Set<CartItem> items = new HashSet<CartItem>();

    private String sessionToken;


    public Integer getId_cart() {
        return id_cart;
    }

    public void setId_cart(Integer id_cart) {
        this.id_cart = id_cart;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        Double sum = 0.0;
        for(CartItem item : this.items) {
            if(item.getGood().getDiscount() == null)
                sum = sum + item.getGood().getPrice()*item.getCount();
            else sum = sum + item.getGood().getDiscount()*item.getCount();
        }
        return sum;
    }

    public int getItemsNumber() {
        return this.items.size();
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_cart == null) ? 0 : id_cart.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((sessionToken == null) ? 0 : sessionToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShoppingCart other = (ShoppingCart) obj;
        if (id_cart == null) {
            if (other.id_cart != null)
                return false;
        } else if (!id_cart.equals(other.id_cart))
            return false;
        if (items == null) {
            if (other.items != null)
                return false;
        } else if (!items.equals(other.items))
            return false;
        if (sessionToken == null) {
            if (other.sessionToken != null)
                return false;
        } else if (!sessionToken.equals(other.sessionToken))
            return false;
        return true;
    }

}
