package com.example.store.repository;

import com.example.store.entity.Category_group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatGroupRepo extends JpaRepository<Category_group,Integer> {
}
