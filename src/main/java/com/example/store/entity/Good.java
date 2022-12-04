package com.example.store.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "good")
@Data
public class Good{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_good")
    private Integer id_good;

    @Column(name = "number")
    private Integer number;

    @Column(name = "name_good")
    private String name_good;

   /* @ManyToOne
    @ToString.Exclude
    private Model model;*/

    @Column (name = "main_photo")
    private byte[] main_photo;

    @Column(name = "description", length = 5000)
    private String description;
    @Column(name = "price")
    private Integer price;


/*
    @ManyToOne
    @ToString.Exclude
    private Category category;

    @ManyToOne
    @ToString.Exclude
    private Producer producer;*/

    public Good() {
    }

    public Integer getId_good() {
        return id_good;
    }

    public void setId_good(Integer id_good) {
        this.id_good = id_good;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName_good() {
        return name_good;
    }

    public void setName_good(String name_good) {
        this.name_good = name_good;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public byte[] getMain_photo() {
        return main_photo;
    }

    public void setMain_photo(byte[] main_photo) {
        this.main_photo = main_photo;
    }
}