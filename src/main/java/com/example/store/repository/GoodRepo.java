package com.example.store.repository;

import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepo extends JpaRepository<Good,Integer> {

}
