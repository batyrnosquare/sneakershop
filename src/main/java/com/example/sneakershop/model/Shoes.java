package com.example.sneakershop.model;


import com.example.sneakershop.constants.Material;
import com.example.sneakershop.constants.Sex;
import com.example.sneakershop.constants.ShoeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "shoes")
public class Shoes extends Item{

    @Enumerated(EnumType.STRING)
    private Material material;

    private ShoeType type;

    private Sex sex;

    public Shoes(Material material, ShoeType type, Sex sex) {

        this.material = material;
        this.type = type;
        this.sex = sex;
    }

    public Shoes() {
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
