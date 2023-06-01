package com.example.store.services;

import com.example.store.dto.BrandDto;
import com.example.store.dto.CategoryDto;
import com.example.store.dto.ModelDto;
import com.example.store.entity.*;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ModelService {

    @Autowired
    public ModelRepo modelRepo;
    @Autowired
    public BrandRepo brandRepo;
    @Autowired
    public ProducerRepo producerRepo;
    @Autowired
    public GoodRepo goodRepo;
    @Autowired
    public ImageRepo imageRepo;
    @Autowired
    public CarRepo carRepo;


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

    public Brand isBrandExists(String name_brand) {
        return brandRepo.findByNameBrand2(name_brand);
    }

    public Model isModelExists(String name_model, Brand brand, Integer year) {
        return modelRepo.findByNameModelAndBrandAndYear(name_model, brand, year);
    }

    public Producer isProducerExists(String name_producer, String country) {
        return producerRepo.findByNameProducerAndCountry(name_producer, country);
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

    public Brand getBrandById(Integer id_brand){
        return brandRepo.findById(id_brand).orElse(null);
    }

    public Model getModelById(Integer id_model){
        return modelRepo.findById(id_model).orElse(null);
    }

    public List<Brand> listAll(String name_brand){
        if (name_brand != null) return brandRepo.findByNameBrand(name_brand);
        return brandRepo.findAll();
    }

    public List<Model> listAllModel(String name_model){
        if (name_model != null) return modelRepo.findByNameModel(name_model);
        return modelRepo.findAll();
    }

    @Transactional
    public void deleteBrand(Integer id_brand) {
        List<Model> models = modelRepo.findByBrand(id_brand);

        for (Model model : models) {
            List<Good> goods = goodRepo.findByModel(model.getId_model());
            for (Good good : goods) {
                imageRepo.deleteAllByGoodId(good.getId_good());
            }
            goodRepo.deleteAllByModelId(model.getId_model());

            carRepo.deleteAllByModelId(model.getId_model());
        }

        modelRepo.deleteByBrandId(id_brand);
        brandRepo.deleteById(id_brand);
    }

    @Transactional
    public void deleteModel(Integer id_model) {

        List<Good> goods = goodRepo.findByModel(id_model);
        for (Good good : goods) {
            imageRepo.deleteAllByGoodId(good.getId_good());
        }
        goodRepo.deleteAllByModelId(id_model);

        carRepo.deleteAllByModelId(id_model);

        modelRepo.deleteById(id_model);
    }

    public List<ModelDto> getModelAndProduct() {
        return modelRepo.getModelAndProduct();
    }

    public Model getModelByNameAndBrand(String modelName, Brand brand) {
        return modelRepo.findByNameModelAndBrand(modelName, brand);
    }

    public void saveModel(Model model) {
        modelRepo.save(model);
    }

}
