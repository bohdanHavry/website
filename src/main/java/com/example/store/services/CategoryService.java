package com.example.store.services;


import com.example.store.dto.CategoryDto;
import com.example.store.entity.*;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    public CategoryRepo categoryRepo;
    @Autowired
    public CatGroupRepo catGroupRepo;
    @Autowired
    public GoodRepo goodRepo;
    @Autowired
    public ImageRepo imageRepo;

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

    public Category_group getCategoryGroupById(Integer id_category_group){
        return catGroupRepo.findById(id_category_group).orElse(null);
    }

    public List<Category_group> listAll(String name_category_group){
        if (name_category_group != null) return catGroupRepo.findByNameCategoryGroup(name_category_group);
        return catGroupRepo.findAll();
    }

    public List<Category> listAllCategory(String name_category){
        if (name_category != null) return categoryRepo.findByNameCategory(name_category);
        return categoryRepo.findAll();
    }

    @Transactional
    public void deleteCategoryGroup(Integer id_category_group) {
        List<Category> categories = categoryRepo.findByCategoryGroup(id_category_group);

        // Видаляємо товари та images, що належать до кожної знайденої категорії
        for (Category category : categories) {
            List<Good> goods = goodRepo.findByCategory(category.getId_category());
            for (Good good : goods) {
                imageRepo.deleteAllByGoodId(good.getId_good());
            }
            goodRepo.deleteAllByCategoryId(category.getId_category());
        }

        categoryRepo.deleteByCategoryGroupId(id_category_group);
        catGroupRepo.deleteById(id_category_group);
    }

    @Transactional
    public void deleteCategory(Integer id_category) {

        List<Good> goods = goodRepo.findByCategory(id_category);
        for (Good good : goods) {
            imageRepo.deleteAllByGoodId(good.getId_good());
        }
        goodRepo.deleteAllByCategoryId(id_category);


        categoryRepo.deleteById(id_category);
    }

    public Category getCategoryById(Integer id_category){
        return categoryRepo.findById(id_category).orElse(null);
    }

    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepo.getCategoryAndProduct();
    }

}
