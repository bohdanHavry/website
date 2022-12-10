package com.example.store.services;

import com.example.store.entity.*;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    public ModelRepo modelRepo;
    @Autowired
    public BrandRepo brandRepo;
    @Autowired
    public ProducerRepo producerRepo;


    public void saveBrandToDB(String name_brand, Brand brand )  {
        brand.setName_brand(name_brand);
        brandRepo.save(brand);
    }

    public void saveProducerToDB(String name_producer, String country, Producer producer )  {
        producer.setName_producer(name_producer);
        producer.setCountry(country);
        producerRepo.save(producer);
    }

    public void saveModelToDB(String name_model, Integer year, Brand brand, Model model) {
        model.setName_model(name_model);
        model.setYear(year);
        model.setBrand(brand);
        modelRepo.save(model);
    }

    public List<Brand> getAllBrand()
    {
        return brandRepo.findAll();
    }

    public List<Model> getAllModel()
    {
        return modelRepo.findAll();
    }

    public List<Producer> getAllProducer()
    {
        return producerRepo.findAll();
    }
}
