package com.example.store.controller;


import com.example.store.entity.*;
import com.example.store.repository.OrderItemRepo;
import com.example.store.repository.OrderRepo;
import com.example.store.repository.UserRepo;
import com.example.store.services.MailService;
import com.example.store.services.MainService;
import com.example.store.services.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
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
    @Autowired
    private MailService mailService;

    @GetMapping("/order")
    public String showOrder(HttpServletRequest request, Model model, Principal principal) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        ShoppingCart shoppingCart = shopCartService.getShoppingCartBySessionToken(sessionToken);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "order";
    }

    @PostMapping("/order")
    public String processOrder(HttpServletRequest request, @RequestParam (required = false) String city,
                               @RequestParam (required = false) String address, @RequestParam String payment_method,
                               @RequestParam String first_name, @RequestParam String last_name, @RequestParam String mail,
                               @RequestParam String phone, @RequestParam String order_method, Principal principal,
                               RedirectAttributes redirectAttributes) {

        User user = mainService.getUserByPrincipal(principal);
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        ShoppingCart shoppingCart = shopCartService.getShoppingCartBySessionToken(sessionToken);

        Order order = new Order();

        order.setUser(user);
        order.setOrder_date(new Date());
        order.setCity(city);
        order.setFirst_name(first_name);
        order.setLast_name(last_name);
        order.setPhone(phone);
        order.setMail(mail);
        order.setAddress(address);
        order.setPayment_method(payment_method);
        order.setPayment_status(false);
        order.setOrder_method(order_method);
        order.setOrder_status("Очікується");
        order.setTotal_price(shoppingCart.getTotalPrice());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : shoppingCart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setGood(cartItem.getGood());
            orderItem.setQuantity(cartItem.getCount());
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        orderRepo.saveAndFlush(order);

        // Очистка товарів з корзини після збереження замовлення
        shoppingCart.getItems().clear();
        shopCartService.saveShoppingCart(shoppingCart);


        if(!StringUtils.isEmpty(user.getMail())){
            String message = String.format(
                    "Привіт, %s! \n" +
                            "Дякуємо за ваше замовлення на сайті AvtoSenat, очікуйте повідомлень від менеджера. Номер вашого замовлення: %s",
                    user.getLogin(),
                    order.getId_order()
            );

            mailService.send(user.getMail(),"Успішне оформлення замовлення!", message);
            redirectAttributes.addFlashAttribute("message", "На вашу поштову скриньку було відправлено повідомлення про успішне оформлення замовлення.");
        }

        return  "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String showConfirmation(Model model, Principal principal) {
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "confirmation";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orderList")
    public String orderList(Model model, Principal principal,
                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                            @RequestParam(required = false) String status) {
        List<Order> orders;
        if (startDate != null && endDate != null) {
            orders = orderRepo.findByOrderDateBetween(startDate, endDate);
        } else if (status != null) {
            orders = orderRepo.findByOrderStatus(status);
        }
        else { orders = orderRepo.findAll();}
        model.addAttribute("orders", orders);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "orderList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/order/{orderId}")
    public String editOrder(@PathVariable("orderId") Long id_order, Principal principal , Model model){
        Order order = orderRepo.findById(id_order).orElse(null);
        List<String> orderStatusList = Arrays.asList("Очікується", "Виконується", "Відправлено", "Доставлено");
        model.addAttribute("order", order);
        model.addAttribute("orderStatusList", orderStatusList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "editOrder";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/changeInfoOrder/{id_order}")
    public String changeInfoOrder(@PathVariable("id_order") Long id_order, RedirectAttributes redirectAttributes,
                                  @RequestParam String order_method, @RequestParam(required = false) String address,
                                  @RequestParam(required = false) String city,
                                  @RequestParam String order_status,
                                  @RequestParam Boolean payment_status) {
        Order order = orderRepo.findById(id_order).orElse(null);
        order.setOrder_method(order_method);
        order.setAddress(address);
        order.setCity(city);
        order.setOrder_status(order_status);
        order.setPayment_status(payment_status);
        orderRepo.save(order);
        redirectAttributes.addFlashAttribute("changeInfoOrder", "Інформацію про замовлення було успішно змінено!");
        return "redirect:/orderList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/order/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long id_order, RedirectAttributes redirectAttributes){
        orderRepo.deleteById(id_order);
        redirectAttributes.addFlashAttribute("changeInfoOrder", "Замовлення було успішно видалено!");
        return "redirect:/orderList";
    }

    @GetMapping("/myOrders")
    public String myOrders(Model model, Principal principal,
                           @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                           @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                           @RequestParam(required = false) String status) {
        String login = principal.getName();
        User user = userRepo.findByLogin(login);
        List<Order> orders;
        if (startDate != null && endDate != null) {
            orders = orderRepo.findByUserAndOrderDateBetween(user, startDate, endDate);
        } else if (status != null) {
            orders = orderRepo.findByUserAndOrderStatus(user, status);
        }
        else {
            orders = orderRepo.findByUser(user);
        }
        model.addAttribute("orders", orders);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "myOrders";
    }

}
