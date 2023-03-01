package com.example.store.controller;

import com.example.store.entity.Role;
import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.MailService;
import com.example.store.services.MainService;
import com.example.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MainService mainService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registration")
    public String registration(Principal principal , Model model){
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if(user.getPassword_user() != null && !user.getPassword_user().equals(user.getConfirm_password_user())){
            model.addAttribute("passwordError", "Паролі різні!");
            return "registration";
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            //model.addAttribute("errors", errors); виводить весь Map

            return "registration";
        }

        User userFromDB = userRepo.findByLogin(user.getLogin());
        if(userFromDB != null){
            model.addAttribute("loginError","Такий логін вже існує!");
            return "registration";
        }

        User userFromDB2 = userRepo.findByMail(user.getMail());
        if(userFromDB2 != null){
            model.addAttribute("mailError","Така поштова скринька вже існує!");
            return "registration";
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword_user(passwordEncoder.encode(user.getPassword_user()));
        user.setConfirm_password_user(passwordEncoder.encode(user.getConfirm_password_user()));
        userRepo.save(user);

        if(!StringUtils.isEmpty(user.getMail())){
            String message = String.format(
                    "Привіт, %s! \n" +
                            "Вітаємо за приєднання до AvtoSenat. Будь-ласка перейдіть по посиланню: http://localhost:8090/activate/%s",
                    user.getLogin(),
                    user.getActivationCode()
            );

            mailService.send(user.getMail(),"Код активації", message);
        }

        String s1 = user.getActivationCode();

        return "redirect:/activate2/"+s1;
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code, Principal principal) {
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "Користувач успішно активований!");
        } else {
            model.addAttribute("message", "Код активації не знайдений!");
        }

        return "login";
    }

    @GetMapping("/activate2/{code2}")
    public String activate2(Model model, @PathVariable String code2, Principal principal) {
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        boolean isActivated = userService.activateUser2(code2);

        if (isActivated) {
            model.addAttribute("message", "Користувач не активований," +
                    " перейдіть будь-ласка до поштової скриньки та натисніть по посиланню, щоб " +
                    "активувати користувача та увійти на сайт!");
        } else {
            model.addAttribute("message", "Код активації не знайдений!");
        }

        return "login";
    }

}
