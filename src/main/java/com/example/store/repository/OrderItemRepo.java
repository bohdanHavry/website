package com.example.store.repository;

import com.example.store.entity.Order;
import com.example.store.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    //List<OrderItem> findByOrder(Order order);
}
