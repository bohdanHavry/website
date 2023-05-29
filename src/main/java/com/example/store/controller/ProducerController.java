package com.example.store.controller;

import com.example.store.entity.Brand;
import com.example.store.entity.Category_group;
import com.example.store.entity.Producer;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.ModelRepo;
import com.example.store.repository.ProducerRepo;
import com.example.store.services.CategoryService;
import com.example.store.services.MainService;
import com.example.store.services.ModelService;
import com.example.store.services.ProducerService;
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
public class ProducerController {
    @Autowired
    private ModelService modelService;
    @Autowired
    private MainService mainService;
    @Autowired
    private ProducerRepo producerRepo;
    @Autowired
    private ProducerService producerService;

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
                                     Producer producer, RedirectAttributes redirectAttributes) {

        Producer isProducerExists = modelService.isProducerExists(name_producer, country);
        if (isProducerExists != null) {
            redirectAttributes.addFlashAttribute("isProducerExists", true);
            return "redirect:/addProducer";
        }
        modelService.saveProducerToDB(name_producer, country, producer);
        return "redirect:/addGood";
    }

    @GetMapping("/showProducer")
    public String showProducer(Model model, Principal principal, @RequestParam(name = "name_producer", required = false) String name_producer) {
        model.addAttribute("producer", producerService.listAll(name_producer));
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "showProducer";
    }

    @GetMapping("/producer/{producerId}")
    public String editProducer(@PathVariable("producerId") Integer id_producer, Principal principal , Model model){
        Producer producer = producerService.getProducerById(id_producer);
        model.addAttribute("producer", producer);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "editProducer";
    }

    @PostMapping("/changeInfoProducer/{id_producer}")
    public String changeInfoProducer(@PathVariable("id_producer") Integer id_producer, RedirectAttributes redirectAttributes,
                                  @RequestParam String name_producer, @RequestParam String country){
        Producer producer = producerService.getProducerById(id_producer);
        producer.setName_producer(name_producer);
        producer.setCountry(country);
        producerRepo.save(producer);
        redirectAttributes.addFlashAttribute("changeInfoProducer", "Інформацію про виробника було успішно змінено!");
        return "redirect:/showProducer";
    }

    @GetMapping("/producer/delete/{producerId}")
    public String deleteProducer(@PathVariable("producerId") Integer id_producer, RedirectAttributes redirectAttributes){
        producerService.deleteProducer(id_producer);
        redirectAttributes.addFlashAttribute("changeInfoProducer", "Виробника було успішно видалено!");
        return "redirect:/showProducer";
    }


}
