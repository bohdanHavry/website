package com.example.store.services;

import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final UserRepo userRepo;

    public User getUserByPrincipal(Principal principal){
        if(principal == null) return new User();
        return userRepo.findByLogin(principal.getName());
    }
}
