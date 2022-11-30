package com.example.store.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "good")
@Data
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id_good")
    private Integer id_good;

    @Column(name = "number")
    private String number;

    @Column(name = "name_good")
    private String name_good;

    @ManyToOne
    @ToString.Exclude
    private Model model;

    @Column (name = "main_photo")
    private Byte main_photo;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @ToString.Exclude
    private Category category;

    @ManyToOne
    @ToString.Exclude
    private Producer producer;

    public Good() {
    }

    public Integer getId_good() {
        return id_good;
    }

    public void setId_good(Integer id_good) {
        this.id_good = id_good;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName_good() {
        return name_good;
    }

    public void setName_good(String name_good) {
        this.name_good = name_good;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Byte getMain_photo() {
        return main_photo;
    }

    public void setMain_photo(Byte main_photo) {
        this.main_photo = main_photo;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}