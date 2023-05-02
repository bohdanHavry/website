package com.example.store.controller;


import com.example.store.entity.ShoppingCart;
import com.example.store.services.MainService;
import com.example.store.services.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private MainService mainService;
    @Autowired
    private ShopCartService shopCartService;

    @GetMapping
    public String showCheckout(HttpServletRequest request, Model model, Principal principal) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        ShoppingCart shoppingCart = shopCartService.getShoppingCartBySessionToken(sessionToken);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "order";
    }

    @PostMapping
    public String processCheckout(Model model,
                                  String name,
                                  String email,
                                  String phone,
                                  String address,
                                  String city,
                                  String state,
                                  String zipcode,
                                  String cardNumber,
                                  String cardExpiration,
                                  String cardCVV) {
        // Process the order and display a confirmation page
        return "confirmation";
    }

}
