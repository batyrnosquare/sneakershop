package com.example.sneakershop.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private String color;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<Sizes> sizes = new ArrayList<>();

    private Double price;



    @JsonCreator
    public Item(@JsonProperty("id") Long id,
                @JsonProperty("name") String name,
                @JsonProperty("brand") String brand,
                @JsonProperty("color") String color,
                @JsonProperty("sizes") List<Sizes> sizes,
                @JsonProperty("price") Double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.sizes = sizes;
        this.price = price;
    }

    public Item() {

    }

}
