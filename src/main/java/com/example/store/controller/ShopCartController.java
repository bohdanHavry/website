package com.example.store.controller;

import antlr.StringUtils;
import com.example.store.entity.*;
import com.example.store.repository.UserRepo;
import com.example.store.services.GoodService;
import com.example.store.services.MainService;
import com.example.store.services.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ShopCartController {
    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private MainService mainService;

    @Autowired
    private GoodService goodService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/addToCart")
    public String addToCart(HttpServletRequest request, Model model, Principal principal,
                            @RequestParam("id_good") Long id_good, @RequestParam("count") Integer count, RedirectAttributes redirectAttributes)
    {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if(sessionToken == null){

            sessionToken = UUID.randomUUID().toString();
            request.getSession().setAttribute("sessionToken", sessionToken);
            shopCartService.addShoppingCart(id_good, sessionToken, count, principal);
        }
        else{
            shopCartService.addToExistingShoppingCart(id_good, sessionToken, count, principal);
        }
        redirectAttributes.addFlashAttribute("addMessage", "Товар успішно доданий до корзини!");
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String showCartView(HttpServletRequest request, Model model, Principal principal){
        model.addAttribute("user", mainService.getUserByPrincipal(principal));

        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if(sessionToken == null){
            model.addAttribute("shoppingCart", new ShoppingCart());
        }
        else {
            ShoppingCart shoppingCart = shopCartService.getShoppingCartBySessionToken(sessionToken);
            model.addAttribute("shoppingCart", shoppingCart);
       }

        return "shoppingCart";
    }

    @PostMapping("/updateShoppingCart")
    public String updateShoppingCart(@RequestParam("item_id") Integer id,
                                 @RequestParam("count") Integer count) {

        shopCartService.updateShoppingCartItem(id, count);
        return "redirect:cart";
    }

    @GetMapping("/removeItem/{id}")
    public String removeItem(@PathVariable("id") Integer id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        System.out.println("got here ");
        shopCartService.removeCartIemFromShoppingCart(id,sessionToken);
        return "redirect:/cart";
    }
}
