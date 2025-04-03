package com.example.sneakershop.model;

import com.example.sneakershop.constants.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "socks")
public class Socks extends Item{

    @Enumerated(EnumType.STRING)
    private Material material;

    public Socks() {
    }

    public Socks(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
