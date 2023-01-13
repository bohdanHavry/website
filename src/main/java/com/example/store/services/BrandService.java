package com.example.store.services;

import com.example.store.dto.BrandDto;
import com.example.store.dto.CatGroupDto;
import com.example.store.entity.Brand;
import com.example.store.entity.Category_group;
import com.example.store.repository.BrandRepo;
import com.example.store.repository.CatGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    public BrandRepo brandRepo;

    public List<BrandDto> getBrandAndProduct() {
        return brandRepo.getBrandAndProduct();
    }

    public Brand getBrandById(Integer id_brand){
        return brandRepo.findById(id_brand).orElse(null);
    }
}
