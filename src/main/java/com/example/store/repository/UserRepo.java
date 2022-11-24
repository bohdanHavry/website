package com.example.store.repository;

import com.example.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}
