package com.example.sneakershop.model;


import com.example.sneakershop.constants.Material;
import com.example.sneakershop.constants.Sex;
import com.example.sneakershop.constants.ShoeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}
