package com.example.store.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category_group")
@Data
public class Category_group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_category_group")
    private Integer id_category_group;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category_group")
    private Set<Category_group> category_groups = new HashSet<>();

    @Column(name = "name_category_group",length = 250)
    private String name_category_group;

    public Category_group() {
    }

    public Integer getId_category_group() {
        return id_category_group;
    }

    public void setId_category_group(Integer id_category_group) {
        this.id_category_group = id_category_group;
    }

    public Set<Category_group> getCategory_groups() {
        return category_groups;
    }

    public void setCategory_groups(Set<Category_group> category_groups) {
        this.category_groups = category_groups;
    }

    public String getName_category_group() {
        return name_category_group;
    }

    public void setName_category_group(String name_category_group) {
        this.name_category_group = name_category_group;
    }
}
