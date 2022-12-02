package com.example.store.controller;

import com.example.store.repository.GoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/addGood")
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodController {
     @Autowired
     private GoodRepo goodRepo;



    @GetMapping("/shop")
    public String goodList(Model model)
    {
        model.addAttribute("goods",goodRepo.findAll());
        return "shop";
    }


    @GetMapping
    public String showAddGood()
    {
        return "addGood";
    }

    /*
    @PostMapping("/addG")
    public String saveGood (@RequestParam("number") Integer number, @RequestParam("name_good") String name_good,
                            @RequestParam("main_photo") Byte main_photo, @RequestParam("description") String description,
                            @RequestParam("price") Integer price, @RequestParam("category_id_category") Integer category_id_category,
                            @RequestParam("model_id_model") Integer model_id_model, @RequestParam("producer_id_producer") Integer producer_id_producer)
    {
        Good good = new Good();
        good.setNumber(number);
        good.setName_good(name_good);
        good.setMain_photo(main_photo);
        good.setDescription(description);
        good.setPrice(price);
        good.setCategory(new Category());
        good.setModel(new Model());
        good.setProducer(new Producer());
        // goodRepo.save(good);
        return "redirect:/shop";
    }*/
}
