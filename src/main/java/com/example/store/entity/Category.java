package com.example.store.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_category")
    private Integer id_category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category")
    private Set<Category> categories = new HashSet<>();

    @Column(name = "name_category",length = 250)
    private String name_category;

    @ManyToOne
    @ToString.Exclude
    private Category_group category_groups;

    public Category() {
    }

    public Integer getId_category() {
        return id_category;
    }

    public void setId_category(Integer id_category) {
        this.id_category = id_category;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public Category_group getCategory_groups() {
        return category_groups;
    }

    public void setCategory_groups(Category_group category_groups) {
        this.category_groups = category_groups;
    }
}
