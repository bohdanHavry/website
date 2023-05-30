package com.example.store.controller;

import com.example.store.dto.*;
import com.example.store.entity.*;
import com.example.store.repository.CarRepo;
import com.example.store.repository.CatGroupRepo;
import com.example.store.repository.GoodRepo;
import com.example.store.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final ReviewService reviewService;
    private final UserService userService;
    private final GoodRepo goodRepo;
    private final CarRepo carRepo;
    private final CategoryService categoryService;
    private final CatGroupService catGroupService;
    private final ProducerService producerService;
    private final BrandService brandService;
    private final ModelService modelService;
    private final CatGroupRepo catGroupRepo;
    private final HttpServletRequest request;

    @GetMapping("/")
    public String main(Principal principal , Model model, @RequestParam(name = "title", required = false) String title){
        List<CatGroupDto> categoryGroups = catGroupService.getCategoryGroupAndProduct();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();
        List<ModelDto> modelDtoList = modelService.getModelAndProduct();

        model.addAttribute("goods", goodService.listAll(title));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("categories", categories);
        model.addAttribute("categoryGroup", categoryGroups);
        model.addAttribute("producer", producerDtoList);
        model.addAttribute("brand", brandDtoList);
        model.addAttribute("model", modelDtoList);

        return "mainPage";
    }


    @GetMapping("/shop")
    public String shop(Principal principal , Model model, @RequestParam(name = "title", required = false) String title){
        List<CatGroupDto> categoryGroups = catGroupService.getCategoryGroupAndProduct();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();
        List<ModelDto> modelDtoList = modelService.getModelAndProduct();

        model.addAttribute("goods", goodService.listAll(title));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("categories", categories);
        model.addAttribute("categoryGroup", categoryGroups);
        model.addAttribute("producer", producerDtoList);
        model.addAttribute("brand", brandDtoList);
        model.addAttribute("model", modelDtoList);

        return "main";
    }

    @GetMapping("/good-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Integer id_category, Model model, Principal principal){
        Category category = categoryService.getCategoryById(id_category);
        List<CatGroupDto> categoryGroups = catGroupService.getCategoryGroupAndProduct();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Good> goodList = goodService.getGoodByCategory(id_category);
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();
        List<ModelDto> modelDtoList = modelService.getModelAndProduct();

        model.addAttribute("category",category);
        model.addAttribute("categories", categories);
        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("producer", producerDtoList);
        model.addAttribute("categoryGroup", categoryGroups);
        model.addAttribute("brand", brandDtoList);
        model.addAttribute("model", modelDtoList);

        return "good-in-category";
    }

    @GetMapping("/good-in-categoryGroup/{id}")
    public String getProductsInCategoryGroup(@PathVariable("id") Integer id_category_group, Model model, Principal principal){
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        Category_group categoryGroup = catGroupService.getCategoryGroupById(id_category_group);
        List<CatGroupDto> categoryGroups = catGroupService.getCategoryGroupAndProduct();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Good> goodList = goodService.getGoodByCategoryGroup(id_category_group);
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();
        List<ModelDto> modelDtoList = modelService.getModelAndProduct();

        model.addAttribute("categoryGroup", categoryGroup);
        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("producer",producerDtoList);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryGroup", categoryGroups);
        model.addAttribute("brand", brandDtoList);
        model.addAttribute("model", modelDtoList);

        return "good-in-categoryGroup";
    }

    @GetMapping("/good-in-producer/{id}")
    public String getProductsInProducer(@PathVariable("id") Integer id_producer, Model model, Principal principal){
        List<CatGroupDto> categoryGroups = catGroupService.getCategoryGroupAndProduct();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        Producer producer = producerService.getProducerById(id_producer);
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<Good> goodList = goodService.getGoodByProducer(id_producer);
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();
        List<ModelDto> modelDtoList = modelService.getModelAndProduct();

        model.addAttribute("categories", categories);
        model.addAttribute("categoryGroup", categoryGroups);
        model.addAttribute("producer",producer);
        model.addAttribute("producerDto", producerDtoList);
        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("brand", brandDtoList);
        model.addAttribute("model", modelDtoList);

        return "good-in-producer";
    }

    @GetMapping("/good-in-brand/{id}")
    public String getProductsInBrand(@PathVariable("id") Integer id_model, Model model, Principal principal){
        List<CatGroupDto> categoryGroups = catGroupService.getCategoryGroupAndProduct();
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<ProducerDto> producerDtoList = producerService.getProducerAndProduct();
        List<Good> goodList = goodService.getGoodByModel(id_model);

        com.example.store.entity.Model model1 = modelService.getModelById(id_model);
        List<BrandDto> brandDtoList = brandService.getBrandAndProduct();
        List<ModelDto> modelDtoList = modelService.getModelAndProduct();

        model.addAttribute("good", goodList);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        model.addAttribute("producer",producerDtoList);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryGroup", categoryGroups);
        model.addAttribute("brand", brandDtoList);
        model.addAttribute("model", modelDtoList);

        return "good-in-brand";
    }

    @GetMapping("/main/{id_good}")
    public String goodInfo(@PathVariable Long id_good, Model model, Principal principal){
        Good good = goodService.getGoodById(id_good);

        List<Review> review = reviewService.getAllReviewsByGoodId(id_good);

        List<Good> relatedProducts = goodService.getProductsByCategoryId(good.getCategory().getId_category());
        model.addAttribute("relatedProducts", relatedProducts);

        User users = mainService.getUserByPrincipal(principal);
        model.addAttribute("users", users);

        if (review.isEmpty()) {
            model.addAttribute("noReviews", true);
        } else             model.addAttribute("review", review);

        model.addAttribute("good", good);
        model.addAttribute("images", good.getImages());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "shop";
    }

    @PostMapping("/main/{id_good}/reviews")
    public String addReview(@PathVariable Long id_good, @ModelAttribute Review review, @RequestParam("rating") int rating, Principal principal) {
        User users = mainService.getUserByPrincipal(principal);
        reviewService.addReview(id_good, users.getUser_id(), rating, review);
        return "redirect:/main/" + id_good;
    }

    @GetMapping("/main/{id_good}/deleteReview/{id_review}")
    public String deleteReview(@PathVariable Long id_good, @PathVariable Long id_review,Principal principal) {
    mainService.deleteReview(id_review, principal);
    return "redirect:/main/" + id_good;
    }

    @GetMapping("/allReviews/{id_good}")
    public String allReviews(@PathVariable Long id_good, Model model, Principal principal) {
        List<Review> review = reviewService.getAllReviewsByGoodId(id_good);
        Good good = goodService.getGoodById(id_good);
        model.addAttribute("good", good);
        model.addAttribute("review", review);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "allReviews";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteGood/{id_good}")
    public String deleteGood(@PathVariable("id_good") Long id_good){
        goodRepo.deleteById(id_good);
        return "redirect:/shop";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editGood/{id_good}")
    public String editGood(@PathVariable("id_good") Long id_good, Principal principal , Model model){
        Good good = goodService.getGoodById(id_good);
        model.addAttribute("good", good);
        model.addAttribute("category", goodService.getAllCategory());
        model.addAttribute("model", goodService.getAllModel());
        model.addAttribute("producer",goodService.getAllProducer());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "changeInfoGood";
    }

    @PostMapping("/changeInfoGood/{id_good}")
    public String changeInfoGood(@PathVariable("id_good") Long id_good, RedirectAttributes redirectAttributes, @RequestParam Integer number, @RequestParam String title,
                                 @RequestParam String description, @RequestParam Integer price, @RequestParam Category category,
                                 @RequestParam com.example.store.entity.Model model, @RequestParam Producer producer){
        Good good = goodService.getGoodById(id_good);
        good.setNumber(number);
        good.setTitle(title);
        good.setDescription(description);
        good.setPrice(price);
        good.setCategory(category);
        good.setModel(model);
        good.setProducer(producer);
        goodRepo.save(good);
        redirectAttributes.addFlashAttribute("changeInfoGood", "Інформацію про товар було успішно змінено!");
        return "redirect:/main/" + id_good;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editImageGood/{id_good}")
    public String editImageGood(@PathVariable("id_good") Long id_good, Principal principal , Model model){
        Good good = goodService.getGoodById(id_good);
        model.addAttribute("good", good);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "changeGoodImage";
    }

    @PostMapping("/editImageGood/{id_good}")
    public String editGoodImage(@PathVariable("id_good") Long id_good, RedirectAttributes redirectAttributes,
                                @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3) throws IOException {
       goodService.editGoodImage(id_good, file1, file2, file3);

        redirectAttributes.addFlashAttribute("changeInfoGood", "Зображення товара було успішно змінено!");
        return "redirect:/main/" + id_good;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/hotDeal/{id_good}")
    public String hotDeal(@PathVariable("id_good") Long id_good) {
        goodService.setHotDeal(id_good, true);
        return "redirect:/shop";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteHotDeal/{id_good}")
    public String deleteHotDeal(@PathVariable("id_good") Long id_good) {
        goodService.deleteHotDeal(id_good, false);
        return "redirect:/shop";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/discount/{id_good}")
    public String showDiscountForm(@PathVariable("id_good") Long id_good, Model model, Principal principal) {
        Good good = goodService.getGoodById(id_good);
        model.addAttribute("good", good);
        model.addAttribute("discount", good.getDiscount());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "discount-form";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/discount/{id_good}")
    public String setDiscount(@PathVariable("id_good") Long id_good,
                              @RequestParam("discount") Integer discount,
                              RedirectAttributes redirectAttributes) {
        try {
            Good good = goodService.getGoodById(id_good);
            if (good == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Товар не знайдено");
                return "redirect:/";
            }
            good.setDiscount(discount);
            goodRepo.save(good);
            redirectAttributes.addFlashAttribute("successMessage", "Знижка на товар \"" + good.getTitle() + "\" успішно встановлена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Сталася помилка: " + e.getMessage());
        }
        return "redirect:/shop";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteDiscount/{id_good}")
    public String deleteDiscount(@PathVariable("id_good") Long id_good){
        Good good = goodService.getGoodById(id_good);
        good.setDiscount(null);
        goodRepo.save(good);
        return "redirect:/shop";
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
    public String profile(Principal principal, Model model) {

        User user = mainService.getUserByPrincipal(principal);
        List<Car> cars = carRepo.findByUserId(user.getUser_id());
        model.addAttribute("cars", cars);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));

        return "profile";
    }

}
