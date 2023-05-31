package com.example.store.repository;

import com.example.store.entity.Category;
import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT p FROM Good p INNER JOIN Model c ON c.id_model = p.model.id_model WHERE c.id_model = ?1")
    public List<Good> findByModel(Integer model_id);

    @Query("SELECT p FROM Good p INNER JOIN Category c ON c.id_category = p.category.id_category INNER JOIN Model m ON m.id_model = p.model.id_model WHERE c.id_category = ?1 AND m.id_model = ?2")
    public List<Good> getGoodByCategoryAndModel(Integer category_id, Integer model_id);

    @Query("SELECT p FROM Good p INNER JOIN p.model.brand b INNER JOIN p.category.category_group cg WHERE b.id_brand = ?1 AND cg.id_category_group = ?2")
    public List<Good> findByBrandAndCategoryGroup(Integer brand_id, Integer category_group_id);

    @Modifying
    @Query("DELETE FROM Good g WHERE g.category.id_category = :categoryId")
    void deleteAllByCategoryId(@Param("categoryId") Integer categoryId);

    @Modifying
    @Query("DELETE FROM Good g WHERE g.model.id_model = :modelId")
    void deleteAllByModelId(@Param("modelId") Integer modelId);

    @Modifying
    @Query("DELETE FROM Good g WHERE g.producer.id_producer = :id_producer")
    void deleteAllByProducerId(@Param("id_producer") Integer id_producer);


    //@Query("SELECT id_good FROM Good INNER JOIN Category ON Good.category_id = Category.id_category")
    //public List<Good> findByCategory(Long id_good);
}
