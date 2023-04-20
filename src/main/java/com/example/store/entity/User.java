package com.example.store.entity;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer user_id;
    @NotBlank(message = "Логін не може бути пустим")
    @Column(name = "login", unique = true)
    private String login;
    @NotBlank(message = "Пароль не може бути пустим")
    @Column(name = "password_user", length = 1000)
    private String password_user;
    @NotBlank(message = "Пароль підтвердження не може бути пустим")
    @Column(name = "confirm_password_user", length = 1000)
    private String confirm_password_user;
    @NotBlank(message = "Ім'я не може бути пусте")
    @Column(name = "first_name")
    private String first_name;
    @NotBlank(message = "Прізвище не може бути пустим")
    @Column(name = "last_name")
    private String last_name;
    @NotBlank(message = "По батькові не може бути пустим")
    @Column(name = "middle_name")
    private String middle_name;
    @NotBlank(message = "Номер телефону не може бути пустим")
    @Column(name = "phone")
    private String phone;

    @Email(message = "Поштова скринька не правильна")
    @NotBlank(message = "Назва поштової скриньки не може бути пустою")
    @Column(name = "mail")
    private String mail;

    @Column(name = "activationCode")
    private String activationCode;

    @Column(name = "resetToken")
    private String resetToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {

    }

    public boolean isAdmin(){return roles.contains(Role.ADMIN);}

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getConfirm_password_user() {
        return confirm_password_user;
    }

    public void setConfirm_password_user(String confirm_password_user) {
        this.confirm_password_user = confirm_password_user;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return getPassword_user();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

