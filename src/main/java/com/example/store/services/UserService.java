package com.example.store.services;

import com.example.store.entity.User;
import com.example.store.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public boolean activateUser2(String code2) {
        User user = userRepo.findByActivationCode(code2);

        if (user != null) {
            return true;
        }

        return false;
    }

    public void changeUserPassword(User user, String newPassword, String confirmPassword) {
        user.setPassword_user(passwordEncoder.encode(newPassword));
        user.setConfirm_password_user(passwordEncoder.encode(confirmPassword));
        userRepo.save(user);
    }

}
