package com.example.store.controller;

import com.example.store.entity.Role;
import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MainService mainService;

    @GetMapping("/registration")
    public String registration(Principal principal , Model model){
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String,Object> model) {
        User userFromDB = userRepo.findByLogin(user.getLogin());

        if(userFromDB != null){
            model.put("message","User exists!");
            return "registration";
        }

        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}
