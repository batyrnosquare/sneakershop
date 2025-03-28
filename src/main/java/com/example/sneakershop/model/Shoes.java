package com.example.sneakershop.model;


import com.example.sneakershop.constants.Material;
import com.example.sneakershop.constants.Sex;
import com.example.sneakershop.constants.ShoeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "shoes")
public class Shoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private String color;

    private Integer size;

    private Double price;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Material material;

    private ShoeType type;

    private Sex sex;

    public Shoes(Long id, String name, String brand, String color, Integer size, Double price, Integer quantity, Material material, ShoeType type, Sex sex) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.material = material;
        this.type = type;
        this.sex = sex;
    }

    public Shoes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public ShoeType getType() {
        return type;
    }

    public void setType(ShoeType type) {
        this.type = type;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
