package com.example.store.controller;

import com.example.store.entity.Good;
import com.example.store.entity.User;
import com.example.store.repository.GoodRepo;
import com.example.store.services.GoodService;
import com.example.store.services.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final GoodService goodService;
    //private final GoodRepo goodRepo;

    @GetMapping("/")
    public String main(Principal principal , Model model, @RequestParam(name = "title", required = false) String title){
        model.addAttribute("goods", goodService.listGood(title));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "main";
    }

    @GetMapping("/main/{id_good}")
    public String goodInfo(@PathVariable Long id_good, Model model){
        Good good = goodService.getGoodById(id_good);
        model.addAttribute("good", good);
        model.addAttribute("images", good.getImages());
        return "shop";
    }

    @PostMapping("/main/delete/{id_good}")
    public String deleteGood(@PathVariable Long id_good){
        goodService.deleteGood(id_good);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
