package com.example.store.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/*@Entity
@Table(name = "user_role")
@Data*/
public enum Role implements GrantedAuthority {
    USER, ADMIN;
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    @Column(name = "name_role")
    private String name;


    public Role() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    @Override
    public String getAuthority() {
        return name();
    }
}
