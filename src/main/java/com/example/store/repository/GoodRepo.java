package com.example.store.repository;

import com.example.store.entity.Category;
import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodRepo extends JpaRepository<Good,Long> {
    //List<Good> findByTitle(String title);

    @Query("SELECT p FROM Good p WHERE p.title LIKE %?1%")
    public List<Good> findByTitle(String title);

    @Query("SELECT p FROM Good p INNER JOIN Category c ON c.id_category = p.category.id_category WHERE c.id_category = ?1")
    public List<Good> findByCategory(Integer category_id);

    @Query("SELECT p FROM Good p INNER JOIN Category_group c ON c.id_category_group = p.category.category_group.id_category_group WHERE c.id_category_group = ?1")
    public List<Good> findByCategoryGroup(Integer category_group_id);

    @Query("SELECT p FROM Good p INNER JOIN Producer c ON c.id_producer = p.producer.id_producer WHERE c.id_producer = ?1")
    public List<Good> findByProducer(Integer producer_id);

    @Query("SELECT p FROM Good p INNER JOIN Brand c ON c.id_brand = p.model.brand.id_brand WHERE c.id_brand = ?1")
    public List<Good> findByBrand(Integer brand_id);


    //@Query("SELECT id_good FROM Good INNER JOIN Category ON Good.category_id = Category.id_category")
    //public List<Good> findByCategory(Long id_good);
}
