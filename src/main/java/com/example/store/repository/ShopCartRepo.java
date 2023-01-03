package com.example.store.repository;

import com.example.store.entity.Good;
import com.example.store.entity.ShoppingCart;
import com.example.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCartRepo extends JpaRepository<ShoppingCart, Integer> {

    public List<ShoppingCart> findByUser (User user);

    public ShoppingCart findByGoodAndUser(User user, Good good);
}
