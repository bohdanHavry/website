package com.example.store.controller;

import com.example.store.dto.CategoryDto;
import com.example.store.entity.Category;
import com.example.store.entity.Good;
import com.example.store.entity.User;
import com.example.store.repository.GoodRepo;
import com.example.store.services.CategoryService;
import com.example.store.services.GoodService;
import com.example.store.services.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final GoodService goodService;
    private final GoodRepo goodRepo;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String main(Principal principal , Model model, @RequestParam(name = "title", required = false) String title, Category category, Integer category_id){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        model.addAttribute("goods", goodService.listAll(title));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("category", categoryDtoList);

        return "main";
    }

    @GetMapping("/good-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Integer id_category, Model model, Principal principal){
        Category category = categoryService.getCategoryById(id_category);
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Good> goodList = goodService.getGoodByCategory(id_category);
        model.addAttribute("category",category);
        model.addAttribute("categories", categories);
        model.addAttribute("good", goodList);

        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "good-in-category";
    }

    @GetMapping("/main/{id_good}")
    public String goodInfo(@PathVariable Long id_good, Model model, Principal principal){
        Good good = goodService.getGoodById(id_good);
        model.addAttribute("good", good);
        model.addAttribute("images", good.getImages());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "shop";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteGood/{id_good}")
    public String deleteGood(@PathVariable("id_good") Long id_good){
        goodRepo.deleteById(id_good);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = mainService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

}
