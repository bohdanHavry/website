package com.example.store.repository;

import com.example.store.dto.BrandDto;
import com.example.store.dto.CatGroupDto;
import com.example.store.entity.Brand;
import com.example.store.entity.Category_group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepo extends JpaRepository<Brand, Integer> {
    @Query("SELECT new com.example.store.dto.BrandDto(c.id_brand, c.name_brand, count(p.model.brand.id_brand)) FROM Brand c INNER JOIN Model g on g.brand.id_brand = c.id_brand INNER JOIN Good p on p.model.id_model = g.id_model GROUP BY c.id_brand")
    List<BrandDto> getBrandAndProduct();

    @Query("SELECT p FROM Brand p WHERE p.name_brand LIKE %?1%")
    public List<Brand> findByNameBrand(String name_brand);
}
