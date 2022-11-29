package com.example.store.controller;

import org.springframework.stereotype.Controller;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        return "main";
    }

}
