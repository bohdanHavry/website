package com.example.store.repository;

import com.example.store.entity.Good;
import com.example.store.entity.Model;
import com.example.store.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r INNER JOIN r.good g WHERE g.id_good = ?1")
    public List<Review> findAllByGoodId(Long id_good);

}
