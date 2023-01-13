package com.example.store.services;

import com.example.store.dto.CategoryDto;
import com.example.store.dto.ProducerDto;
import com.example.store.entity.Category;
import com.example.store.entity.Producer;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.ProducerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {
    @Autowired
    public ProducerRepo producerRepo;

    public Producer getProducerById(Integer id_producer){
        return producerRepo.findById(id_producer).orElse(null);
    }

    public List<ProducerDto> getProducerAndProduct() {
        return producerRepo.getProducerAndProduct();
    }

}
