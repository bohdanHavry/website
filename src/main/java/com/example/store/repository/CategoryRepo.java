package com.example.store.repository;

import com.example.store.entity.Category;
import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.id_category LIKE ?1")
    public List<Category> findByCategory(Integer id_category);
}
