package com.example.store.repository;

import com.example.store.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepo extends JpaRepository<Producer, Integer> {
}
