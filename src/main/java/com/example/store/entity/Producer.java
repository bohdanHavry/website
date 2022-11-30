package com.example.store.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "producer")
@Data
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_producer")
    private Integer id_producer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_producer")
    private Set<Producer> producers = new HashSet<>();

    @Column(name = "name_producer",length = 250)
    private String name_producer;

    @Column(name = "country")
    private String country;

    public Producer() {
    }

    public Integer getId_producer() {
        return id_producer;
    }

    public void setId_producer(Integer id_producer) {
        this.id_producer = id_producer;
    }

    public Set<Producer> getProducers() {
        return producers;
    }

    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    public String getName_producer() {
        return name_producer;
    }

    public void setName_producer(String name_producer) {
        this.name_producer = name_producer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
