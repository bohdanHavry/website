package com.example.store.services;

import com.example.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    //@Value("${spring.mail.username}")
   // private String username;

    public void send(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("213chos@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public void sendPasswordResetLink(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("213chos@gmail.com");
        message.setSubject("Запит на зміну пароля на сайті AvtoSenat");
        message.setText("Для зміни пароля користувача перейдіть по наступному посиланню:\n" + token);
        mailSender.send(message);
    }

}
