package com.example.store.controller;


import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.MailService;
import com.example.store.services.MainService;
import com.example.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.thymeleaf.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;


@Controller
public class ChangeInfoController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    MainService mainService;
    @Autowired
    MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/changePassword")
    public String changePassword(Principal principal , Model model){
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePasswordPost(User user, Model model, RedirectAttributes redirectAttributes, @RequestParam String first_name, @RequestParam String last_name,
                                 @RequestParam String middle_name, @RequestParam String phone, @RequestParam String password_user,
                                 @RequestParam String confirm_password_user, @RequestParam String login, @RequestParam String mail, @RequestParam String old_password_user) {
        User userFromDB = userRepo.findByLogin(user.getLogin());

        if (!passwordEncoder.matches(old_password_user, userFromDB.getPassword_user()))
        {
            model.addAttribute("message", "Неправильно введено поточний пароль");
            return "changePassword";
        }
        if(passwordEncoder.matches(password_user, userFromDB.getPassword_user()))
        {
            model.addAttribute("message", "Старий і новий паролі співпадають!");
            return "changePassword";
        }
        if(password_user != null && !password_user.equals(confirm_password_user)){
            model.addAttribute("message", "Нові паролі не співпадають!");
            return "changePassword";
        }

        userFromDB.setPassword_user(passwordEncoder.encode(password_user));
        userFromDB.setConfirm_password_user(passwordEncoder.encode(confirm_password_user));
        userFromDB.setLogin(login);
        userFromDB.setMail(mail);
        userFromDB.setFirst_name(first_name);
        userFromDB.setLast_name(last_name);
        userFromDB.setMiddle_name(middle_name);
        userFromDB.setPhone(phone);
        userRepo.save(userFromDB);
        redirectAttributes.addFlashAttribute("changeInfoMessage", "Пароль було успішно змінено!");
        //model.addAttribute("changeInfoMessage", "Персональну інформацію було успішно змінено!");
        return "redirect:/profile";
    }

    @GetMapping("/changeMail")
    public String changeMail(Principal principal , Model model){
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "changeMail";
    }

    @PostMapping("/changeMail")
    public String changeMailPost(User user, Model model, RedirectAttributes redirectAttributes, @RequestParam String first_name, @RequestParam String last_name,
                                     @RequestParam String middle_name, @RequestParam String phone, @RequestParam String password_user,
                                     @RequestParam String confirm_password_user, @RequestParam String login, @RequestParam String mail, HttpServletRequest request) {
        User userFromDB = userRepo.findByLogin(user.getLogin());

        if (!passwordEncoder.matches(password_user, userFromDB.getPassword_user()))
        {
            model.addAttribute("message", "Неправильно введено поточний пароль");
            return "changeMail";
        }

        String userMail = userFromDB.getMail();

        boolean isEmailChanged = (mail != null && !mail.equals(userMail)) ||
                (userMail != null && !userMail.equals(mail));

        if (isEmailChanged) {
            userFromDB.setMail(mail);

            if (!StringUtils.isEmpty(mail)) {
                userFromDB.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if(!org.thymeleaf.util.StringUtils.isEmpty(userFromDB.getMail())){
            String message = String.format(
                    "Привіт, %s! \n" +
                            "Поштова скринька була успішно змінена. Будь-ласка перейдіть по посиланню: %s://%s:%d/activate/%s",
                    userFromDB.getLogin(),
                    request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    userFromDB.getActivationCode()
            );

            mailService.send(userFromDB.getMail(),"Код активації", message);
        }

        userFromDB.setPassword_user(passwordEncoder.encode(password_user));
        userFromDB.setConfirm_password_user(confirm_password_user);
        userFromDB.setLogin(login);
        userFromDB.setFirst_name(first_name);
        userFromDB.setLast_name(last_name);
        userFromDB.setMiddle_name(middle_name);
        userFromDB.setPhone(phone);

        userRepo.save(userFromDB);
        redirectAttributes.addFlashAttribute("changeInfoMessage", "Поштова скринька змінена, для того щоб увійти підтвердьте її! Для цього перейдіть по посиланню, що було надіслено на вказану поштову скриньку!");
        //model.addAttribute("changeInfoMessage", "Персональну інформацію було успішно змінено!");
        return "redirect:/login";
    }

}
