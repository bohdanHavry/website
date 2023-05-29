package com.example.store.repository;

import com.example.store.dto.CategoryDto;
import com.example.store.dto.ProducerDto;
import com.example.store.entity.Brand;
import com.example.store.entity.Model;
import com.example.store.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProducerRepo extends JpaRepository<Producer, Integer> {
    @Query("SELECT new com.example.store.dto.ProducerDto(c.id_producer, c.name_producer, c.country, count(p.producer.id_producer)) FROM Producer c INNER JOIN Good p on p.producer.id_producer = c.id_producer GROUP BY c.id_producer")
    List<ProducerDto> getProducerAndProduct();

    @Query("SELECT p FROM Producer p WHERE p.name_producer LIKE %?1%")
    public List<Producer> findByNameProducer(String name_producer);

    @Query("SELECT m FROM Producer m WHERE m.name_producer = :name_producer AND m.country = :country")
    Producer findByNameProducerAndCountry(String name_producer, String country);
}
