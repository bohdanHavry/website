package com.example.store.repository;

import com.example.store.entity.Category;
import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodRepo extends JpaRepository<Good,Long> {
    //List<Good> findByTitle(String title);

    @Query("SELECT p FROM Good p WHERE p.title LIKE %?1%")
    public List<Good> findByTitle(String title);

    @Query("SELECT p FROM Good p WHERE 1=1 AND category_id=?1")
    public List<Good> findByCategory(Integer category_id);

    //@Query("SELECT id_good FROM Good INNER JOIN Category ON Good.category_id = Category.id_category")
    //public List<Good> findByCategory(Long id_good);
}
