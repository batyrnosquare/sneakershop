package com.example.sneakershop.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id
    private Long id;

    private String name;

    private String brand;

    private String color;

    @OneToMany(mappedBy = "item")
    private List<Sizes> sizes = new ArrayList<>();

    private Double price;

    private Integer quantity;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
