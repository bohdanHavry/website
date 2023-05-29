package com.example.store.controller;

import com.example.store.entity.Brand;
import com.example.store.entity.Car;
import com.example.store.entity.User;
import com.example.store.repository.BrandRepo;
import com.example.store.repository.CarRepo;
import com.example.store.repository.ModelRepo;
import com.example.store.services.BrandService;
import com.example.store.services.MainService;
import com.example.store.services.ModelService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CarController {
    @Autowired
    public CarRepo carRepo;
    @Autowired
    public BrandService brandService;
    @Autowired
    public ModelService modelService;
    @Autowired
    public MainService mainService;

    @GetMapping("/addCar")
    public String showAddCarForm(Model model, Principal principal) {

        model.addAttribute("car", new Car());
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "addCarForm";
    }

    @PostMapping("/addCar")
    public String addCar(@ModelAttribute("car") Car car, Principal principal, RedirectAttributes redirectAttributes) {
        User user = mainService.getUserByPrincipal(principal);
        // Отримання даних про машину з VIN Decoder API
        String vin = car.getVin();
        String apiUrl = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevinextended/" + vin + "?format=json";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String jsonData = response.getBody();

        // Розбір отриманих даних JSON
        String brandName = "";
        String modelName = "";
        int year = 0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonData);
            JsonNode resultsNode = root.get("Results");

            if (resultsNode.isArray()) {
                for (JsonNode resultNode : resultsNode) {
                    String variable = resultNode.get("Variable").asText();
                    String value = resultNode.get("Value").asText();

                    if (variable.equals("Make")) {
                        brandName = value;
                    } else if (variable.equals("Model")) {
                        modelName = value;
                    } else if (variable.equals("Model Year")) {
                        year = Integer.parseInt(value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Обробка винятків при розборі JSON
        }

        // Перевірка, чи існує бренд у базі даних, і створення, якщо не існує
        Brand brand = brandService.getBrandByName(brandName);
        if (brand == null) {
            brand = new Brand();
            brand.setName_brand(brandName);
            brandService.saveBrand(brand);
        }

        // Перевірка, чи існує модель у базі даних, і створення, якщо не існує
        com.example.store.entity.Model model = modelService.getModelByNameAndBrand(modelName, brand);
        if (model == null) {
            model = new com.example.store.entity.Model();
            model.setName_model(modelName);
            model.setYear(year);
            model.setBrand(brand);
            modelService.saveModel(model);
        }

        // Збереження автомобіля в базу даних
        car.setModel(model);
        car.setUser(user);
        carRepo.save(car);

        redirectAttributes.addFlashAttribute("message", "Автомобіль успішно додано до гаражу!");
        return "redirect:/garage";
    }

    @GetMapping("/garage")
    public String showGarage(Model model, Principal principal) {
        User user = mainService.getUserByPrincipal(principal);
        List<Car> cars = carRepo.findByUserId(user.getUser_id());
        model.addAttribute("cars", cars);
        model.addAttribute("user", mainService.getUserByPrincipal(principal));
        return "garage";
    }

}
