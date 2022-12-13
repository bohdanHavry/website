package com.example.store.controller;


import com.example.store.entity.Brand;
import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.repository.BrandRepo;
import com.example.store.repository.CatGroupRepo;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.ModelRepo;
import com.example.store.services.CategoryService;
import com.example.store.services.MainService;
import com.example.store.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class ModelController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private MainService mainService;
    @Autowired
    private ModelRepo modelRepo;
    @Autowired
    private BrandRepo brandRepo;

    @GetMapping("/addModel")
    public String showAddModel(Model model, Principal principal)
    {
        model.addAttribute("brand", modelService.getAllBrand());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addModel";
    }

    @PostMapping("/addBr")
    public String saveBrand (@RequestParam("name_brand") String name_brand, Brand brand) {

        modelService.saveBrandToDB(name_brand, brand);
        return "redirect:/addModel";
    }

    @PostMapping("/addMd")
    public String saveModel (@RequestParam ("name_model") String name_model,
                             @RequestParam ("year") Integer year,
                             Brand brand, com.example.store.entity.Model model) {

        modelService.saveModelToDB(name_model, year, brand, model);
        return "redirect:/addGood";
    }


}

