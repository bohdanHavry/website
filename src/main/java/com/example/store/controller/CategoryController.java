package com.example.store.controller;


import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.entity.Good;
import com.example.store.repository.CatGroupRepo;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.GoodRepo;
import com.example.store.services.CategoryService;
import com.example.store.services.GoodService;
import com.example.store.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CatGroupRepo catGroupRepo;
    @Autowired
    private MainService mainService;
    @Autowired
    private GoodRepo goodRepo;

    @GetMapping("/addCategory")
    public String showAddCategory(Model model, Principal principal)
    {
        model.addAttribute("category_group", categoryService.getAllCategoryGroup());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addCategory";
    }

    @PostMapping("/addCG")
    public String saveCategoryGroup (@RequestParam ("name_category_group") String name_category_group, Category_group category_group) {

        categoryService.saveCategoryGroupToDB(name_category_group, category_group);
        return "redirect:/addCategory";
    }

    @PostMapping("/addC")
    public String saveCategory (@RequestParam ("name_category") String name_category, Category_group category_group, Category category) {

        categoryService.saveCategoryToDB(name_category,category_group, category );
        return "redirect:/addGood";
    }

    @GetMapping("/showCategory")
    public String showCategory(Model model, Principal principal, @RequestParam(name = "name_category_group", required = false) String name_category_group,
                               @RequestParam(name = "name_category", required = false) String name_category) {
        model.addAttribute("category_group", categoryService.listAll(name_category_group));
        model.addAttribute("category", categoryService.listAllCategory(name_category));

        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "showCategory";
    }

    @GetMapping("/catGroup/{catGroupId}")
    public String editCatGroup(@PathVariable("catGroupId") Integer id_category_group, Principal principal , Model model){
        Category_group category_group = categoryService.getCategoryGroupById(id_category_group);
        model.addAttribute("category_group", category_group);
        model.addAttribute("categoryMy", category_group);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "editCategory";
    }

    @PostMapping("/changeInfoCatGroup/{id_category_group}")
    public String changeInfoCatGroup(@PathVariable("id_category_group") Integer id_category_group, RedirectAttributes redirectAttributes,
                                     @RequestParam String name_category_group){
        Category_group category_group = categoryService.getCategoryGroupById(id_category_group);
        category_group.setName_category_group(name_category_group);
        catGroupRepo.save(category_group);
        redirectAttributes.addFlashAttribute("changeInfoCategory", "Назву категорії було успішно змінено!");
        return "redirect:/showCategory";
    }

    @GetMapping("/catGroup/delete/{catGroupId}")
    public String deleteCatGroup(@PathVariable("catGroupId") Integer id_category_group, RedirectAttributes redirectAttributes){

        categoryService.deleteCategoryGroup(id_category_group);
        redirectAttributes.addFlashAttribute("changeInfoCategory", "Категорію було успішно видалено!");
        return "redirect:/showCategory";
    }

    @GetMapping("/category/{categoryId}")
    public String editCategory(@PathVariable("categoryId") Integer id_category, Principal principal , Model model){
        Category category = categoryService.getCategoryById(id_category);
        model.addAttribute("category", category);
        model.addAttribute("category_group", categoryService.getAllCategoryGroup());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "editCategory";
    }

    @PostMapping("/changeInfoCategory/{id_category}")
    public String changeInfoCategory(@PathVariable("id_category") Integer id_category, RedirectAttributes redirectAttributes,
                                     @RequestParam("name_category") String name_category,@RequestParam Category_group category_group){
        Category category = categoryService.getCategoryById(id_category);
        category.setName_category(name_category);
        category.setCategory_group(category_group);
        categoryRepo.save(category);
        redirectAttributes.addFlashAttribute("changeInfoCategory", "Інформацію про підкатегорію було успішно змінено!");
        return "redirect:/showCategory";
    }

    @GetMapping("/category/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Integer id_category, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id_category);
        redirectAttributes.addFlashAttribute("changeInfoCategory", "Підкатегорію було успішно видалено!");
        return "redirect:/showCategory";
    }


}
