package com.example.store.controller;

import com.example.store.entity.Good;
import com.example.store.entity.Image;
import com.example.store.entity.ShoppingCart;
import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import com.example.store.services.GoodService;
import com.example.store.services.MainService;
import com.example.store.services.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

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

    @GetMapping ("/cart")
    public String showShoppingCart (Model model, Principal principal, Image image, Good good)
    {
        User user = mainService.getUserByPrincipal(principal);
        List <ShoppingCart> shoppingCarts = shopCartService.listCartItem(user);
        model.addAttribute("shopCart", shoppingCarts);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("images", image);
        model.addAttribute("goods", good);
        return "shoppingCart";
    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id_good") Long goodId,
            @RequestParam(value = "count", required = false, defaultValue = "1") Integer count,
            Principal principal, HttpServletRequest request){

        if(principal == null){
            return "redirect:/login";
        }
        Good good = goodService.getGoodById(goodId);
        String username = principal.getName();
        User user =  userRepo.findByLogin(username);

        Integer cart = shopCartService.addGoodToCart(goodId, count, user);
        return "redirect:" + request.getHeader("Referer");

    }
}
