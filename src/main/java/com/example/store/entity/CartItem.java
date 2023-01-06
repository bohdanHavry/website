package com.example.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cartItem")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cartItem")
    private Integer id_cartItem;

    @Column(name = "count")
    private Integer count;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne (cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_good")
    private Good good;

    @ManyToOne (cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId_cartItem() {
        return id_cartItem;
    }

    public void setId_cartItem(Integer id_cartItem) {
        this.id_cartItem = id_cartItem;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((id_cartItem == null) ? 0 : id_cartItem.hashCode());
        result = prime * result + ((good == null) ? 0 : good.hashCode());
        result = prime * result + count;
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
        CartItem other = (CartItem) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (id_cartItem == null) {
            if (other.id_cartItem != null)
                return false;
        } else if (!id_cartItem.equals(other.id_cartItem))
            return false;
        if (good == null) {
            if (other.good != null)
                return false;
        } else if (!good.equals(other.good))
            return false;
        if (count != other.count)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CartItem [id_cartItem=" + id_cartItem + ", good=" + good + "]";
    }
}
