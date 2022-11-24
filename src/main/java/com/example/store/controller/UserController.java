package com.example.store.controller;

import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping
    public ResponseEntity registration (@RequestBody User user){

        try {
            return ResponseEntity.ok("Server is live!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error!");
        }
    }


    @GetMapping("/")
    public ResponseEntity getUsers (){
        try {
            return ResponseEntity.ok("Server is live!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error!");
        }
    }
}
