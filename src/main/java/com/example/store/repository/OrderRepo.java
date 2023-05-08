package com.example.store.repository;

import com.example.store.entity.Order;
import com.example.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    @Query("SELECT o FROM Order o WHERE o.order_date BETWEEN :startDate AND :endDate")
    List<Order> findByOrderDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    @Query("SELECT o FROM Order o WHERE o.order_status = :status")
    List<Order> findByOrderStatus(@Param("status") String status);

    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.order_date BETWEEN :startDate AND :endDate")
    List<Order> findByUserAndOrderDateBetween(@Param("user") User user, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT o FROM Order o WHERE o.user = :user AND o.order_status = :status")
    List<Order> findByUserAndOrderStatus(@Param("user") User user, @Param("status") String status);
}
