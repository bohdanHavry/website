package com.example.store.repository;

import com.example.store.entity.Brand;
import com.example.store.entity.Category;
import com.example.store.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ModelRepo extends JpaRepository<Model, Integer> {

    @Query("SELECT c FROM Model c WHERE c.brand.id_brand = :id_brand")
    List<Model> findByBrand(@Param("id_brand") Integer id_brand);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM model WHERE brand_id = :brandId", nativeQuery = true)
    void deleteByBrandId(@Param("brandId") Integer brandId);

    @Query("SELECT p FROM Model p WHERE p.name_model LIKE %?1%")
    public List<Model> findByNameModel(String name_model);
}
