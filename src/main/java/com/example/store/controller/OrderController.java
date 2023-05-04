package com.example.store.controller;


import com.example.store.entity.*;
import com.example.store.repository.OrderItemRepo;
import com.example.store.repository.OrderRepo;
import com.example.store.repository.UserRepo;
import com.example.store.services.MainService;
import com.example.store.services.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private MainService mainService;
    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;

    @GetMapping
    public String showOrder(HttpServletRequest request, Model model, Principal principal) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        ShoppingCart shoppingCart = shopCartService.getShoppingCartBySessionToken(sessionToken);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "order";
    }

    @PostMapping
    public String processOrder(@RequestParam String city, @RequestParam String region,
                               @RequestParam Integer index, @RequestParam String address, @RequestParam String payment_method, Principal principal,
                               Model model, @ModelAttribute("shoppingCart") ShoppingCart shoppingCart) {

        User user = mainService.getUserByPrincipal(principal);

        Order order = new Order();

        order.setUser(user);
        order.setCity(city);
        order.setRegion(region);
        order.setIndex(index);
        order.setAddress(address);
        order.setPayment_method(payment_method);
        order.setPayment_status(false);
        order.setOrder_status("Очікується");

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : shoppingCart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setGood(cartItem.getGood());
            orderItem.setQuantity(cartItem.getCount());
            orderItem.setTotal_price(shoppingCart.getTotalPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        orderItemRepo.saveAll(orderItems); // зберігаємо OrderItems
        orderRepo.save(order); // зберігаємо замовлення разом з OrderItems

        //model.addAttribute("order", order);

        return  "redirect:/confirmation";
    }



}
