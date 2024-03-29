package com.example.store.services;

import com.example.store.entity.Good;
import com.example.store.entity.User;
import com.example.store.repository.CartItemRepo;
import com.example.store.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public void changeUserPassword(User user, String newPassword, String confirmPassword) {
        user.setPassword_user(passwordEncoder.encode(newPassword));
        user.setConfirm_password_user(passwordEncoder.encode(confirmPassword));
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException("Користувача не знайдено");
        }

        if (user.getActivationCode() != null && !user.getActivationCode().isEmpty()) {
            request.getSession().setAttribute("errorMessage", "Ваш акаунт не активований, перейдіть будь-ласка до поштової " +
                    "скриньки та натисніть по посиланню для активації акаунту.");
            throw new UsernameNotFoundException("Ваш акаунт не активований, перейдіть будь-ласка до поштової " +
                    "скриньки та натисніть по посиланню для активації акаунту.");
        }

        return user;
    }

    public User findById(Integer user_id) {
        return userRepo.findById(user_id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public void deleteUserById(Integer userId) {
        //cartItemRepo.deleteByUserId(userId);
        userRepo.deleteById(userId);
    }

    public List<User> listAll(String login){
        if (login != null) return userRepo.findByUserLogin(login);
        return userRepo.findAll();
    }
}
