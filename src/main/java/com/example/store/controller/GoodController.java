package com.example.store.controller;

import com.example.store.entity.Category;
import com.example.store.entity.Good;
import com.example.store.entity.Model;
import com.example.store.entity.Producer;
import com.example.store.repository.GoodRepo;
import com.example.store.services.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodController {
     @Autowired
     private GoodRepo goodRepo;
     @Autowired
     private GoodService goodService;


    @GetMapping("/shop")
    public String goodList(org.springframework.ui.Model model)
    {
        model.addAttribute("goods",goodRepo.findAll());
        return "shop";
    }


    @GetMapping("/addGood")
    public String showAddGood()
    {
        return "addGood";
    }


    @PostMapping("/addG")
    public String saveGood (@RequestParam("file") MultipartFile file,
                            @RequestParam("number") Integer number, @RequestParam("name_good") String name_good,
                            @RequestParam("description") String description, @RequestParam("price") Integer price
                            )
    {
        goodService.saveGoodToDB(file,number,name_good,description,price);
        return "redirect:/shop";
    }
}
