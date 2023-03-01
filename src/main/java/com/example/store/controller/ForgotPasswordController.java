package com.example.store.controller;

import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.MailService;
import com.example.store.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ForgotPasswordController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailService mailService;
    @Autowired
    private MainService mainService;

    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(@RequestParam("login") String login,
                                            HttpServletRequest request,
                                            Model model) {

        User user = userRepo.findByLogin(login);
        if (user == null) {
            model.addAttribute("loginError", "Користувача з таким логіном не знайдено");
            return "forgotPassword";
        }
        String token = UUID.randomUUID().toString();
        user.setActivationCode(token);
        userRepo.save(user);
        // Create a link to the password reset form
        String resetUrl = getResetUrl(request, token);

        // Send the password reset link to the user's email
        mailService.sendPasswordResetLink(user.getMail(), resetUrl);

        model.addAttribute("message", "Посилання на форму зміни паролю було відправлено на вашу електронну адресу");

        return "forgotPassword";
    }
    private String getResetUrl(HttpServletRequest request, String token) {
        return String.format("%s://%s:%d/resetPassword?token=%s", request.getScheme(), request.getServerName(),
                request.getServerPort(), token);
    }


}
