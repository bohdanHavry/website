package com.example.store.repository;

import com.example.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("SELECT p FROM User p WHERE p.login LIKE %?1%")
    public List<User> findByUserLogin(String login);
    User findByLogin(String login);
    User findByMail(String mail);
    User findByActivationCode(String code);

    User findByResetToken(String resetToken);
}
