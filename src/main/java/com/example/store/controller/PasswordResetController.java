package com.example.store.controller;

import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.MainService;
import com.example.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
public class PasswordResetController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    MainService mainService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView showResetPasswordForm(String token) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepo.findByResetToken(token);
        if (user == null) {
            modelAndView.addObject("message", "Недісний токен підтвердження");
            modelAndView.setViewName("error");
        } else {
            modelAndView.addObject("login", user.getLogin());
            modelAndView.setViewName("resetPassword");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView processResetPasswordForm(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams,
                                                 Principal principal , Model model) {

        User user = userRepo.findByLogin(requestParams.get("login"));

        if (user == null) {
            modelAndView.addObject("message", "Невірний логін користувача");
            modelAndView.setViewName("error");
            return modelAndView;
        }

        String newPassword = requestParams.get("password_user");
        String confirmPassword = requestParams.get("confirm_password_user");

        if (!newPassword.equals(confirmPassword)) {
            modelAndView.addObject("message", "Паролі не співпадають!");
            modelAndView.addObject("login", user.getLogin());
            modelAndView.setViewName("resetPassword");
            return modelAndView;
        }

        userService.changeUserPassword(user, newPassword, confirmPassword);
        modelAndView.addObject("message", "Пароль змінено успішно");
        modelAndView.addObject("user", mainService.getUserByPrincipal(principal));
        modelAndView.setViewName("login");

        return modelAndView;
    }

}
