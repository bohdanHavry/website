package com.example.store.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_category")
    private Integer id_category;

    @ManyToOne (cascade = CascadeType.REFRESH ,fetch = FetchType.LAZY)
    @JoinColumn(name = "category_group_id")
    private Category_group category_group;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Good> good;


    @Column(name = "name_category",length = 250)
    private String name_category;

    public Category() {
    }

    public Integer getId_category() {
        return id_category;
    }

    public void setId_category(Integer id_category) {
        this.id_category = id_category;
    }

    public Set<Good> getGood() {
        return good;
    }

    public void setGood(Set<Good> good) {
        this.good = good;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public Category_group getCategory_group() {
        return category_group;
    }

    public void setCategory_group(Category_group category_group) {
        this.category_group = category_group;
    }
}
