package com.example.store.services;

import com.example.store.entity.Good;
import com.example.store.repository.GoodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodService {

    @Autowired
    private GoodRepo repo;

    public List<Good> listAll() {
        return repo.findAll();
    }


}
