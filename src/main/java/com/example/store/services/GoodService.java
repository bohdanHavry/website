package com.example.store.services;

import com.example.store.entity.Good;
import com.example.store.repository.GoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class GoodService {
    @Autowired
    public GoodRepo goodRepo;

    public void saveGoodToDB(MultipartFile file, Integer number, String name_good, String description, Integer price){
        Good good = new Good();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains(".."))
        {
            System.out.println("not a a valid file");
        }
        try {
            good.setMain_photo(Base64.getEncoder().encode(file.getBytes()));
        }  catch (IOException e){
            e.printStackTrace();
        }
        good.setNumber(number);
        good.setName_good(name_good);
        good.setDescription(description);
        good.setPrice(price);
        goodRepo.save(good);
    }
}
