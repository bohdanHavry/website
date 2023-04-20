package com.example.store.services;

import com.example.store.entity.Good;
import com.example.store.entity.Review;
import com.example.store.entity.User;
import com.example.store.repository.GoodRepo;
import com.example.store.repository.ReviewRepo;
import com.example.store.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepo reviewRepository;
    @Autowired
    private GoodRepo goodRepo;
    @Autowired
    private UserRepo userRepo;

    public List<Review> getAllReviewsByGoodId(Long id_good) {
        return reviewRepository.findAllByGoodId(id_good);
    }

    public void addReview(Long id_good, Integer user_id, int rating, Review review) {
        Good good = goodRepo.findById(id_good)
                .orElseThrow(() -> new IllegalArgumentException("Invalid good Id:" + id_good));

        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user_id));

        review.setUser(user);
        review.setGood(good);
        review.setReviewDate(LocalDate.now());
        review.setRating(rating);


        List<Review> reviews = good.getReviews();
        reviews.add(review);

        goodRepo.save(good);
    }
}