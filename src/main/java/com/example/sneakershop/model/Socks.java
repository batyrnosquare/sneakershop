package com.example.sneakershop.model;

import com.example.sneakershop.constants.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Socks{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private String color;

    private int size;

    private double price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Material material;
}
