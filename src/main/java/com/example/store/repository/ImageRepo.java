package com.example.store.repository;

import com.example.store.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ImageRepo extends JpaRepository<Image,Integer> {
    // Видаляє всі зображення за id товару
    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.good.id_good = :id_good")
    void deleteAllByGoodId(@Param("id_good") Long id_good);
}
