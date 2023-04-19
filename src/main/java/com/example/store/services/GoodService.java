package com.example.store.services;

import com.example.store.entity.*;
import com.example.store.repository.CategoryRepo;
import com.example.store.repository.GoodRepo;
import com.example.store.repository.ModelRepo;
import com.example.store.repository.ProducerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class GoodService {
    @Autowired
    public GoodRepo goodRepo;
    @Autowired
    public ModelRepo modelRepo;
    @Autowired
    public CategoryRepo categoryRepo;
    @Autowired
    public ProducerRepo producerRepo;



    public void saveGoodToDB(MultipartFile file, MultipartFile file2, MultipartFile file3,
                             Good good, Category category, Model model, Producer producer) throws IOException {
        Image image1;
        Image image2;
        Image image3;
        if(file.getSize() != 0) {
            image1 = toImageEntity(file);
            image1.setPreviewImage(true);
            good.addImageToGood(image1);
        }

        if(file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            good.addImageToGood(image2);
        }

        if(file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            good.addImageToGood(image3);
        }

        Good goodFromDb = goodRepo.save(good);
        goodFromDb.setPreviewImageId(goodFromDb.getImages().get(0).getImage_id());
        good.setCategory(category);
        good.setModel(model);
        good.setProducer(producer);
        goodRepo.save(good);
    }

    private Image toImageEntity(MultipartFile file) throws IOException{
        Image image = new Image();
        image.setName_image(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public List<Good> listAll(String title){
        if (title != null) return goodRepo.findByTitle(title);
        return goodRepo.findAll();
    }

    public Good getGoodById(Long id_good){
        return goodRepo.findById(id_good).orElse(null);
    }

    public List<Good> getGoodByCategory(Integer category_id){
        return goodRepo.findByCategory(category_id);
    }

    public List<Good> getGoodByCategoryGroup(Integer category_group_id){
        return goodRepo.findByCategoryGroup(category_group_id);
    }

    public List<Good> getGoodByProducer(Integer producer_id){
        return goodRepo.findByProducer(producer_id);
    }

    public List<Good> getGoodByBrand(Integer brand_id){
        return goodRepo.findByBrand(brand_id);
    }

    public List<Category> getAllCategory()
    {
        return categoryRepo.findAll();
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
