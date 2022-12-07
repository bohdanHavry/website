package com.example.store.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_brand")
    private Integer id_brand;

    //@OneToMany(cascade = CascadeType.ALL)
   // @JoinColumn(name = "id_brand")
   // private Set<Brand> brands = new HashSet<>();

   // @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    //        mappedBy = "brand")
   // private List<Brand> brand = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_brand")
    private Set<Brand> brands = new HashSet<>();

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


    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }

    public Set<Brand> getBrands() {
        return brands;
    }

    public void setBrands(Set<Brand> brands) {
        this.brands = brands;
    }
}
