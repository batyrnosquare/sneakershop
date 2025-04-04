package com.example.sneakershop.model;


import com.example.sneakershop.constants.Material;
import com.example.sneakershop.constants.Sex;
import com.example.sneakershop.constants.ShoeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "shoes")
public class Shoes extends Item{

    @Enumerated(EnumType.STRING)
    private Material material;

    @Enumerated(EnumType.STRING)
    private ShoeType type;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    public Shoes(Material material, ShoeType type, Sex sex) {

        this.material = material;
        this.type = type;
        this.sex = sex;
    }

    public Shoes() {
    }

}
