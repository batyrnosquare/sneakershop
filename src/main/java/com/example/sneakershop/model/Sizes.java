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

    public Sizes(Long id, Item item, Integer size, Integer quantity, Long itemId) {
        this.id = id;
        this.item = item;
        this.size = size;
        this.quantity = quantity;
        this.itemId = itemId;
    }

    public Sizes(Item item) {
        this.item = item;
    }

}
