package com.example.store.repository;

import com.example.store.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodRepo extends JpaRepository<Good,Long> {
    List<Good> findByTitle(String title);

    //@Query("Insert into good(name_good) values (?1)")
   // void addGood(String modelname);
        // execute custom sql insert query
//        insert into good(par1 , par2, model_id) values (par1, par2, (select id from model where name = {modelname}))

}
