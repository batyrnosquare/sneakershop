package com.example.sneakershop.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Sizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer size;

    private Integer quantity;

    @Transient
    private Long itemId;

    public Sizes() {
    }

}
