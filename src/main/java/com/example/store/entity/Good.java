package com.example.store.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "good")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Good{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_good")
    private Long id_good;

    @Column(name = "number")
    private Integer number;

    @Column(name = "name_good")
    private String title;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "good")
    private List<Image> images = new ArrayList<>();
    private Integer previewImageId;

    @Column(name = "description", length = 5000)
    private String description;
    @Column(name = "price")
    private Integer price;

    public Long getId_good() {
        return id_good;
    }

    public void setId_good(Long id_good) {
        this.id_good = id_good;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Integer getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Integer previewImageId) {
        this.previewImageId = previewImageId;
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

    public void addImageToGood(Image image){
        image.setGood(this);
        images.add(image);
    }
}