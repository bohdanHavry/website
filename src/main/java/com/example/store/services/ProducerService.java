package com.example.store.services;

import com.example.store.dto.CategoryDto;
import com.example.store.dto.ProducerDto;
import com.example.store.entity.Brand;
import com.example.store.entity.Category;
import com.example.store.entity.Good;
import com.example.store.entity.Producer;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.GoodRepo;
import com.example.store.repository.ImageRepo;
import com.example.store.repository.ProducerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProducerService {
    @Autowired
    public ProducerRepo producerRepo;
    @Autowired
    public GoodRepo goodRepo;
    @Autowired
    public ImageRepo imageRepo;

    public Producer getProducerById(Integer id_producer){
        return producerRepo.findById(id_producer).orElse(null);
    }

    public List<ProducerDto> getProducerAndProduct() {
        return producerRepo.getProducerAndProduct();
    }

    public List<Producer> listAll(String name_producer){
        if (name_producer != null) return producerRepo.findByNameProducer(name_producer);
        return producerRepo.findAll();
    }

    @Transactional
    public void deleteProducer(Integer id_producer) {

        List<Good> goods = goodRepo.findByProducer(id_producer);
        for (Good good : goods) {
            imageRepo.deleteAllByGoodId(good.getId_good());
        }
        goodRepo.deleteAllByProducerId(id_producer);

        producerRepo.deleteById(id_producer);
    }

}
