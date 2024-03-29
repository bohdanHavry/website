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

    @Column(name = "isHotDeal")
    private Boolean isHotDeal;

    @Column(name = "discount")
    private Integer discount;

    @ManyToOne (cascade = CascadeType.REFRESH ,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne (cascade = CascadeType.REFRESH ,fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne (cascade = CascadeType.REFRESH ,fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Boolean getHotDeal() {
        return isHotDeal;
    }

    public void setHotDeal(Boolean hotDeal) {
        isHotDeal = hotDeal;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void addImageToGood(Image image){
        image.setGood(this);
        images.add(image);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}