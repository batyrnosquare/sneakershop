package com.example.sneakershop.model;


import jakarta.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
