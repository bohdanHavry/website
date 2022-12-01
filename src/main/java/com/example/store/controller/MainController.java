package com.example.store.controller;

import com.example.store.entity.User;
import com.example.store.services.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/")
    public String main(Principal principal ,Model model){
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "main";
    }

    @GetMapping("/shop")
    public String shop(Map<String, Object> model){
        return "shop";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
