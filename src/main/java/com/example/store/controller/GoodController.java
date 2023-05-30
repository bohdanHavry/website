package com.example.store.controller;

import com.example.store.entity.Category;
import com.example.store.entity.Good;
import com.example.store.entity.Producer;
import com.example.store.repository.GoodRepo;
import com.example.store.services.GoodService;
import com.example.store.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodController {
     @Autowired
     private GoodRepo goodRepo;
     @Autowired
     private GoodService goodService;
     @Autowired
     private MainService mainService;

    @GetMapping("/addGood")
    public String showAddGood(Model model, Principal principal, @RequestParam(name = "title", required = false) String title)
    {
        model.addAttribute("goods", goodService.listAll(title));
        model.addAttribute("categoryGroup", goodService.getAllCategoryGroup());
        model.addAttribute("brand", goodService.getAllBrand());
        model.addAttribute("producer",goodService.getAllProducer());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addGood";
    }


    @PostMapping("/addG")
    public String saveGood (@RequestParam("file") MultipartFile file, @RequestParam("file2") MultipartFile file2,
                            @RequestParam("file3") MultipartFile file3,
                            Good good, Category category,
                            com.example.store.entity.Model model, Producer producer, RedirectAttributes redirectAttributes) throws IOException{
        goodService.saveGoodToDB(file, file2, file3, good, category, model, producer);
        redirectAttributes.addFlashAttribute("addMessage", "Товар успішно був створений!");
        return "redirect:/shop";
    }

    @GetMapping("/getSubcategories")
    @ResponseBody
    public List<Category> getSubcategories(@RequestParam("category") Integer category) {
        // Логіка для отримання підкатегорій залежно від вибраної категорії
        List<Category> subcategories = goodService.getSubcategoriesByCategory(category);
        return subcategories;
    }

    @GetMapping("/getModels")
    @ResponseBody
    public List<com.example.store.entity.Model> getModels(@RequestParam("model") Integer model) {
        List<com.example.store.entity.Model> models = goodService.getModelsByBrand(model);
        return models;
    }


}
