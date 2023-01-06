package com.example.store.repository;

import com.example.store.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCartRepo extends JpaRepository<ShoppingCart, Integer> {
    ShoppingCart findBySessionToken(String sessionToken);
}
