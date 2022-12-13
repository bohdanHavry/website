package com.example.store.controller;

import com.example.store.entity.Category_group;
import com.example.store.entity.Producer;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.ModelRepo;
import com.example.store.repository.ProducerRepo;
import com.example.store.services.CategoryService;
import com.example.store.services.MainService;
import com.example.store.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class ProducerController {
    @Autowired
    private ModelService modelService;
    @Autowired
    private MainService mainService;
    @Autowired
    private ProducerRepo producerRepo;

    @GetMapping("/addProducer")
    public String showAddCategory(Model model, Principal principal)
    {
        model.addAttribute("producer", modelService.getAllProducer());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addProducer";
    }

    @PostMapping("/addPr")
    public String saveProducerGroup (@RequestParam("name_producer") String name_producer,
                                     @RequestParam("country") String country,
                                     Producer producer) {

        modelService.saveProducerToDB(name_producer, country, producer);
        return "redirect:/addGood";
    }
}
