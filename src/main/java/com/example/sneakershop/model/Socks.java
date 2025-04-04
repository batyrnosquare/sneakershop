package com.example.sneakershop.model;

import com.example.sneakershop.constants.Material;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
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

}
