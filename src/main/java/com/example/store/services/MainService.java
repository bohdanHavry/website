package com.example.store.services;

import com.example.store.entity.Good;
import com.example.store.entity.Review;
import com.example.store.entity.Role;
import com.example.store.entity.User;
import com.example.store.repository.ReviewRepo;
import com.example.store.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final UserRepo userRepo;
    private final ReviewRepo reviewRepo;


    public User getUserByPrincipal(Principal principal){
        if(principal == null) return new User();
        return userRepo.findByLogin(principal.getName());
    }

    public void deleteReview(Long id_review, Principal principal) {
        Review review = reviewRepo.findById(id_review).orElse(null);
        User currentUser = getUserByPrincipal(principal);
        if (currentUser.isAdmin() || review.getUser().equals(currentUser)) {
            reviewRepo.deleteById(id_review);
        }
    }

}
