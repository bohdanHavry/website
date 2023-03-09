package com.example.store.controller;


import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.MainService;
import com.example.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.security.Principal;


@Controller
public class ChangeInfoController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MainService mainService;

    @GetMapping("/changeInfo")
    public String changeInfo(Principal principal , Model model){
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "changeInfo";
    }

    @PostMapping("/changeInfo")
    public String changeInfoPost(User user, Model model, RedirectAttributes redirectAttributes, @RequestParam String first_name, @RequestParam String last_name,
                                 @RequestParam String middle_name, @RequestParam String phone, @RequestParam String password_user,
                                 @RequestParam String confirm_password_user, @RequestParam String login, @RequestParam String mail) {
        User userFromDB = userRepo.findByLogin(user.getLogin());
        userFromDB.setPassword_user(password_user);
        userFromDB.setConfirm_password_user(confirm_password_user);
        userFromDB.setLogin(login);
        userFromDB.setMail(mail);
        userFromDB.setFirst_name(first_name);
        userFromDB.setLast_name(last_name);
        userFromDB.setMiddle_name(middle_name);
        userFromDB.setPhone(phone);
        userRepo.save(userFromDB);
        redirectAttributes.addFlashAttribute("changeInfoMessage", "Персональну інформацію було успішно змінено!");
        //model.addAttribute("changeInfoMessage", "Персональну інформацію було успішно змінено!");
        return "redirect:/profile";
    }

}
