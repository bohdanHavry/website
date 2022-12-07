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

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "model")
   // private List<Model> model = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_model")
    private Set<Model> models = new HashSet<>();

    @Column(name = "name_model",length = 250)
    private String name_model;

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @Column(name = "year")
    private String year;

    @ManyToOne
    @ToString.Exclude
    private Brand brand;

   // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    //        mappedBy = "model")
    //private List<Good> good = new ArrayList<>();

    public Model() {
    }

    public Integer getId_model() {
        return id_model;
    }

    public void setId_model(Integer id_model) {
        this.id_model = id_model;
    }

    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
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
}
