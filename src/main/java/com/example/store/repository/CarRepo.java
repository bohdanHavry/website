package com.example.store.repository;

import com.example.store.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepo extends JpaRepository<Car, Long>  {
    @Query("SELECT c FROM Car c WHERE c.user.user_id = :userId")
    List<Car> findByUserId(@Param("userId") Integer userId);
}
