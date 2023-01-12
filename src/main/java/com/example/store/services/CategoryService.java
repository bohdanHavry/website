package com.example.store.services;


import com.example.store.dto.CategoryDto;
import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.entity.Good;
import com.example.store.entity.Image;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    public CategoryRepo categoryRepo;
    @Autowired
    public CatGroupRepo catGroupRepo;

    public void saveCategoryGroupToDB(String name_category_group, Category_group category_group )  {
        category_group.setName_category_group(name_category_group);
        catGroupRepo.save(category_group);
    }

    public void saveCategoryToDB(String name_category, Category_group category_group, Category category) {
        category.setName_category(name_category);
        category.setCategory_group(category_group);
        categoryRepo.save(category);
    }

    public List<Category_group> getAllCategoryGroup()
    {
        return catGroupRepo.findAll();
    }

    public List<Category> listAll(Integer id_category){
        if (id_category != null) return categoryRepo.findByCategory(id_category);
        return categoryRepo.findAll();
    }

    public Category getCategoryById(Integer id_category){
        return categoryRepo.findById(id_category).orElse(null);
    }

    public List<Category> getAllCategory()
    {
        return categoryRepo.findAll();
    }

    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepo.getCategoryAndProduct();
    }

}
