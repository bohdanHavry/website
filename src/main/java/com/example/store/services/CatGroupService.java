package com.example.store.services;

import com.example.store.dto.CatGroupDto;
import com.example.store.dto.CategoryDto;
import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.repository.CatGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CatGroupService {
    @Autowired
    public CatGroupRepo catGroupRepo;

    public List<CatGroupDto> getCategoryGroupAndProduct() {
        return catGroupRepo.getCategoryGroupAndProduct();
    }

    public Category_group getCategoryGroupById(Integer id_category_group){
        return catGroupRepo.findById(id_category_group).orElse(null);
    }
}
