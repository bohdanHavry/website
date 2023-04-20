package com.example.store.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
    @Table(name = "review")
    public class Review {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id_review;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        private String text;

        private int rating;

        @Column(name = "reviewDate")
        private LocalDate reviewDate;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_good")
        private Good good;

        public Long getId_review() {
            return id_review;
        }

        public void setId_review(Long id_review) {
            this.id_review = id_review;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public Good getGood() {
            return good;
        }

        public void setGood(Good good) {
            this.good = good;
        }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}

