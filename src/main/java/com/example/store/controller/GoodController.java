package com.example.store.controller;

import com.example.store.entity.Category;
import com.example.store.entity.Good;
import com.example.store.entity.Producer;
import com.example.store.repository.GoodRepo;
import com.example.store.services.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodController {
     @Autowired
     private GoodRepo goodRepo;
     @Autowired
     private GoodService goodService;

    @GetMapping("/addGood")
    public String showAddGood(Model model)
    {
        model.addAttribute("category", goodService.getAllCategory());
        model.addAttribute("model", goodService.getAllModel());
        model.addAttribute("producer",goodService.getAllProducer());
        return "addGood";
    }


    @PostMapping("/addG")
    public String saveGood (@RequestParam("file") MultipartFile file, Good good, Category category,
                            com.example.store.entity.Model model, Producer producer) throws IOException{
        goodService.saveGoodToDB(file, good, category, model, producer);
        return "redirect:/";
    }


}
