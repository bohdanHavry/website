package com.example.store.controller;


import com.example.store.entity.Brand;
import com.example.store.entity.Category;
import com.example.store.entity.Category_group;
import com.example.store.repository.BrandRepo;
import com.example.store.repository.CatGroupRepo;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.ModelRepo;
import com.example.store.services.CategoryService;
import com.example.store.services.MainService;
import com.example.store.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class ModelController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private MainService mainService;
    @Autowired
    private ModelRepo modelRepo;
    @Autowired
    private BrandRepo brandRepo;

    @GetMapping("/addModel")
    public String showAddModel(Model model, Principal principal)
    {
        model.addAttribute("brand", modelService.getAllBrand());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addModel";
    }

    @PostMapping("/addBr")
    public String saveBrand (@RequestParam("name_brand") String name_brand, Brand brand) {

        modelService.saveBrandToDB(name_brand, brand);
        return "redirect:/addModel";
    }

    @PostMapping("/addMd")
    public String saveModel (@RequestParam ("name_model") String name_model,
                             @RequestParam ("year") Integer year,
                             Brand brand, com.example.store.entity.Model model) {

        modelService.saveModelToDB(name_model, year, brand, model);
        return "redirect:/addGood";
    }

    @GetMapping("/showBrandAndModel")
    public String showBrandAndModel(Model model, Principal principal, @RequestParam(name = "name_brand", required = false) String name_brand,
                                    @RequestParam(name = "name_model", required = false) String name_model) {
        model.addAttribute("brand", modelService.listAll(name_brand));
        model.addAttribute("model", modelService.listAllModel(name_model));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "showBrandAndModel";
    }

    @GetMapping("/brand/{brandId}")
    public String editBrand(@PathVariable("brandId") Integer id_brand, Principal principal , Model model){
        Brand brand = modelService.getBrandById(id_brand);
        model.addAttribute("brand", brand);
        model.addAttribute("brandMy", brand);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "editBrandAndModel";
    }

    @PostMapping("/changeInfoBrand/{id_brand}")
    public String changeInfoBrand(@PathVariable("id_brand") Integer id_brand, RedirectAttributes redirectAttributes,
                                     @RequestParam String name_brand){
        Brand brand = modelService.getBrandById(id_brand);
        brand.setName_brand(name_brand);
        brandRepo.save(brand);
        redirectAttributes.addFlashAttribute("changeInfoBrandAndModel", "Назву бренда було успішно змінено!");
        return "redirect:/showBrandAndModel";
    }

    @GetMapping("/brand/delete/{brandId}")
    public String deleteBrand(@PathVariable("brandId") Integer id_brand, RedirectAttributes redirectAttributes){
        modelService.deleteBrand(id_brand);
        redirectAttributes.addFlashAttribute("changeInfoBrandAndModel", "Бренд було успішно видалено!");
        return "redirect:/showBrandAndModel";
    }

    @GetMapping("/model/{modelId}")
    public String editModel(@PathVariable("modelId") Integer id_model, Principal principal , Model model){
        com.example.store.entity.Model model1 = modelService.getModelById(id_model);
        model.addAttribute("model", model1);
        model.addAttribute("brand", modelService.getAllBrand());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "editBrandAndModel";
    }

    @PostMapping("/changeInfoModel/{id_model}")
    public String changeInfoModel(@PathVariable("id_model") Integer id_model, RedirectAttributes redirectAttributes,
                                     @RequestParam("name_model") String name_model, @RequestParam("year") Integer year,
                                  @RequestParam Brand brand){
        com.example.store.entity.Model model = modelService.getModelById(id_model);
        model.setName_model(name_model);
        model.setYear(year);
        model.setBrand(brand);
        modelRepo.save(model);
        redirectAttributes.addFlashAttribute("changeInfoBrandAndModel", "Інформацію про модель було успішно змінено!");
        return "redirect:/showBrandAndModel";
    }

    @GetMapping("/model/delete/{modelId}")
    public String deleteModel(@PathVariable("modelId") Integer id_model, RedirectAttributes redirectAttributes) {
        modelService.deleteModel(id_model);
        redirectAttributes.addFlashAttribute("changeInfoBrandAndModel", "Модель було успішно видалено!");
        return "redirect:/showBrandAndModel";
    }

}

