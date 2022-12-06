package com.example.store.services;

import com.example.store.entity.Good;
import com.example.store.entity.Image;
import com.example.store.repository.GoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class GoodService {
    @Autowired
    public GoodRepo goodRepo;

    public void saveGoodToDB(MultipartFile file, Good good) throws IOException {
        Image image1;
        if(file.getSize() != 0) {
            image1 = toImageEntity(file);
            image1.setPreviewImage(true);
            good.addImageToGood(image1);
        }
        Good goodFromDb = goodRepo.save(good);
        goodFromDb.setPreviewImageId(goodFromDb.getImages().get(0).getImage_id());
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

    public List<Good> listGood(String title){
        if (title != null) return goodRepo.findByTitle(title);
        return goodRepo.findAll();
    }

    public void deleteGood(Long id_good){
        goodRepo.deleteById(id_good);
    }

    public Good getGoodById(Long id_good){
        return goodRepo.findById(id_good).orElse(null);
    }
}
