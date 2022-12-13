package com.example.store.controller;


import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.entity.Good;
import com.example.store.repository.CatGroupRepo;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.GoodRepo;
import com.example.store.services.CategoryService;
import com.example.store.services.GoodService;
import com.example.store.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CatGroupRepo catGroupRepo;
    @Autowired
    private MainService mainService;

    @GetMapping("/addCategory")
    public String showAddCategory(Model model, Principal principal)
    {
        model.addAttribute("category_group", categoryService.getAllCategoryGroup());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addCategory";
    }

    @PostMapping("/addCG")
    public String saveCategoryGroup (@RequestParam ("name_category_group") String name_category_group, Category_group category_group) {

        categoryService.saveCategoryGroupToDB(name_category_group, category_group);
        return "redirect:/addCategory";
    }

    @PostMapping("/addC")
    public String saveCategory (@RequestParam ("name_category") String name_category, Category_group category_group, Category category) {

        categoryService.saveCategoryToDB(name_category,category_group, category );
        return "redirect:/addGood";
    }
}
