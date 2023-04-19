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

import java.io.IOException;
import java.security.Principal;


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
    public String showAddGood(Model model, Principal principal)
    {
        model.addAttribute("category", goodService.getAllCategory());
        model.addAttribute("model", goodService.getAllModel());
        model.addAttribute("producer",goodService.getAllProducer());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addGood";
    }


    @PostMapping("/addG")
    public String saveGood (@RequestParam("file") MultipartFile file, @RequestParam("file2") MultipartFile file2,
                            @RequestParam("file3") MultipartFile file3,
                            Good good, Category category,
                            com.example.store.entity.Model model, Producer producer) throws IOException{
        goodService.saveGoodToDB(file, file2, file3, good, category, model, producer);
        return "redirect:/";
    }


}
