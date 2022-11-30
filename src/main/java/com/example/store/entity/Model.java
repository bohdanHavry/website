package com.example.store.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "model")
@Data
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_model")
    private Integer id_model;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_model")
    private Set<Good> goods = new HashSet<>();

    @Column(name = "name_model",length = 250)
    private String name_model;

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @Column(name = "year")
    private String year;

    @ManyToOne
    @ToString.Exclude
    private Brand brand;

    public Model() {
    }

    public Integer getId_model() {
        return id_model;
    }

    public void setId_model(Integer id_model) {
        this.id_model = id_model;
    }

    public Set<Good> getGoods() {
        return goods;
    }

    public void setGoods(Set<Good> goods) {
        this.goods = goods;
    }

    public String getName_model() {
        return name_model;
    }

    public void setName_model(String name_model) {
        this.name_model = name_model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
