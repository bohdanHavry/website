package com.example.store.repository;

import com.example.store.dto.CategoryDto;
import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    //@Query("SELECT c FROM Category c WHERE c.id_category LIKE ?1")
   // public List<Category> findByCategory(Integer id_category);
    @Query("SELECT c FROM Category c WHERE c.category_group.id_category_group IN :categoryIds")
    List<Category> findByCategoryGroupIn(@Param("categoryIds") List<Integer> categoryIds);

    @Query("SELECT new com.example.store.dto.CategoryDto(c.id_category, c.name_category, c.category_group.id_category_group, count(p.category.id_category)) FROM Category c INNER JOIN Good p on p.category.id_category = c.id_category INNER JOIN Category_group cg on cg.id_category_group = c.category_group.id_category_group WHERE 1=1 GROUP BY c.id_category")
    List<CategoryDto> getCategoryAndProduct();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM category WHERE category_group_id = :groupId", nativeQuery = true)
    void deleteByCategoryGroupId(@Param("groupId") Integer groupId);

    @Query("SELECT c FROM Category c WHERE c.category_group.id_category_group = :id_category_group")
    List<Category> findByCategoryGroup(@Param("id_category_group") Integer id_category_group);

    @Query("SELECT p FROM Category p WHERE p.name_category LIKE %?1%")
    public List<Category> findByNameCategory(String name_category);
}