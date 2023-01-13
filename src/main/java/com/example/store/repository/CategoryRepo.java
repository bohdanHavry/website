package com.example.store.repository;

import com.example.store.dto.CategoryDto;
import com.example.store.entity.Category;
import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    //@Query("SELECT c FROM Category c WHERE c.id_category LIKE ?1")
   // public List<Category> findByCategory(Integer id_category);

    @Query("SELECT new com.example.store.dto.CategoryDto(c.id_category, c.name_category, count(p.category.id_category)) FROM Category c INNER JOIN Good p on p.category.id_category = c.id_category GROUP BY c.id_category")
    List<CategoryDto> getCategoryAndProduct();

}
