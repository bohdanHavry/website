package com.example.store.controller;

import com.example.store.entity.Role;
import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.MainService;
import com.example.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MainService mainService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(Model model, Principal principal, @RequestParam(name = "login", required = false) String login) {
        model.addAttribute("users", userService.listAll(login));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("userP", mainService.getUserByPrincipal(principal));
        return "userEdit";
    }

    @GetMapping("/delete/{userId}")
    public String userDelete(@PathVariable("userId") Integer userId, Model model, Principal principal) {
        userService.deleteUserById(userId);
        model.addAttribute("userP", mainService.getUserByPrincipal(principal));
        return "redirect:/user";
    }

    @PostMapping
    public String userSave(
            @RequestParam String login,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        user.setLogin(login);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()){
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);

        return "redirect:/user";
    }


}
