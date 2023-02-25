package com.example.store.repository;

import com.example.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Integer> {
    User findByLogin(String login);
    User findByMail(String mail);
    User findByActivationCode(String code);
}
