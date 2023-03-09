package com.example.store.controller;

import com.example.store.dto.BrandDto;
import com.example.store.dto.CatGroupDto;
import com.example.store.dto.CategoryDto;
import com.example.store.dto.ProducerDto;
import com.example.store.entity.*;
import com.example.store.repository.GoodRepo;
import com.example.store.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final GoodService goodService;
    private final GoodRepo goodRepo;
    private final CategoryService categoryService;
    private final CatGroupService catGroupService;
    private final ProducerService producerService;
    private final BrandService brandService;
    private final HttpServletRequest request;

    @GetMapping("/")
    public String main(Principal principal , Model model, @RequestParam(name = "title", required = false) String title){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<CatGroupDto> catGroupDtoList = catGroupService.getCategoryGroupAndProduct();
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();

        model.addAttribute("goods", goodService.listAll(title));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("category", categoryDtoList);
        model.addAttribute("categoryGroup", catGroupDtoList);
        model.addAttribute("producer", producerDtoList);
        model.addAttribute("brand", brandDtoList);

        return "main";
    }

    @GetMapping("/good-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Integer id_category, Model model, Principal principal){
        Category category = categoryService.getCategoryById(id_category);
        List<CatGroupDto> catGroupDtoList = catGroupService.getCategoryGroupAndProduct();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Good> goodList = goodService.getGoodByCategory(id_category);
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();


        model.addAttribute("category",category);
        model.addAttribute("categories", categories);
        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("producer", producerDtoList);
        model.addAttribute("categoryGroup", catGroupDtoList);
        model.addAttribute("brand", brandDtoList);

        return "good-in-category";
    }

    @GetMapping("/good-in-categoryGroup/{id}")
    public String getProductsInCategoryGroup(@PathVariable("id") Integer id_category_group, Model model, Principal principal){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        Category_group categoryGroup = catGroupService.getCategoryGroupById(id_category_group);
        List<CatGroupDto> catGroups = catGroupService.getCategoryGroupAndProduct();
        List<Good> goodList = goodService.getGoodByCategoryGroup(id_category_group);
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();



        model.addAttribute("categoryGroup", categoryGroup);
        model.addAttribute("catGroups", catGroups);
        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("producer",producerDtoList);
        model.addAttribute("category", categoryDtoList);
        model.addAttribute("brand", brandDtoList);

        return "good-in-categoryGroup";
    }

    @GetMapping("/good-in-producer/{id}")
    public String getProductsInProducer(@PathVariable("id") Integer id_producer, Model model, Principal principal){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<CatGroupDto> catGroupDtoList = catGroupService.getCategoryGroupAndProduct();
        Producer producer = producerService.getProducerById(id_producer);
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<Good> goodList = goodService.getGoodByProducer(id_producer);
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();


        model.addAttribute("category", categoryDtoList);
        model.addAttribute("categoryGroup", catGroupDtoList);
        model.addAttribute("producer",producer);
        model.addAttribute("producerDto", producerDtoList);
        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("brand", brandDtoList);

        return "good-in-producer";
    }

    @GetMapping("/good-in-brand/{id}")
    public String getProductsInBrand(@PathVariable("id") Integer id_brand, Model model, Principal principal){
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        Brand brand = brandService.getBrandById(id_brand);
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();
        List<Good> goodList = goodService.getGoodByBrand(id_brand);
        List<CatGroupDto> catGroupDtoList = catGroupService.getCategoryGroupAndProduct();

        model.addAttribute("categoryGroup", catGroupDtoList);
        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("producer",producerDtoList);
        model.addAttribute("category", categoryDtoList);
        model.addAttribute("brand", brandDtoList);

        return "good-in-brand";
    }

    @GetMapping("/main/{id_good}")
    public String goodInfo(@PathVariable Long id_good, Model model, Principal principal){
        Good good = goodService.getGoodById(id_good);
        model.addAttribute("good", good);
        model.addAttribute("images", good.getImages());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "shop";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteGood/{id_good}")
    public String deleteGood(@PathVariable("id_good") Long id_good){
        goodRepo.deleteById(id_good);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "rememberMe", required = false) String rememberMe,
                        HttpServletResponse response, RedirectAttributes redirectAttributes) {

        // Якщо користувач вибрав опцію "Запам'ятати пароль"
        if (rememberMe != null && rememberMe.equals("true")) {
            // Створення cookie з іменем "rememberMe" і значенням "true", з терміном життя 30 днів
            Cookie rememberMeCookie = new Cookie("rememberMe", "true");
            rememberMeCookie.setMaxAge(30 * 24 * 60 * 60); // 30 днів
            response.addCookie(rememberMeCookie);
        } else {
            // Видалення cookie з іменем "rememberMe", якщо такий існує
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("rememberMe")) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/login-error")
    public String loginError(Principal principal, Model model) {
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("loginError", true);
        String errorMessage = (String) request.getSession().getValue("errorMessage");
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model, @ModelAttribute("changeInfoMessage") String changeInfoMessage) {
        User user = mainService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("changeInfoMessage", changeInfoMessage);
        return "profile";
    }

}
