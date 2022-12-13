package com.example.store.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "model")
@Data
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_model")
    private Integer id_model;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Good> good;

    @Column(name = "name_model",length = 250)
    private String name_model;

    @Column(name = "year")
    private Integer year;

    @ManyToOne (cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Model() {
    }

    public Integer getId_model() {
        return id_model;
    }

    public void setId_model(Integer id_model) {
        this.id_model = id_model;
    }

    public Set<Good> getGood() {
        return good;
    }

    public void setGood(Set<Good> good) {
        this.good = good;
    }

    public String getName_model() {
        return name_model;
    }

    public void setName_model(String name_model) {
        this.name_model = name_model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
