package com.example.store.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_brand")
    private Integer id_brand;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_brand")
    private Set<Model> models = new HashSet<>();

    @Column(name = "name_brand",length = 250)
    private String name_brand;

    public Brand() {
    }

    public Integer getId_brand() {
        return id_brand;
    }

    public void setId_brand(Integer id_brand) {
        this.id_brand = id_brand;
    }

    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }
}
