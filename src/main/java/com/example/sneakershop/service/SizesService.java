package com.example.sneakershop.service;

import com.example.sneakershop.model.Item;
import com.example.sneakershop.model.Sizes;
import com.example.sneakershop.repository.ItemRepository;
import com.example.sneakershop.repository.SizesRepository;
import org.springframework.stereotype.Service;

@Service
public class SizesService {

    private final SizesRepository sizesRepository;

    private final ItemRepository itemRepository;


    public SizesService(SizesRepository sizesRepository, ItemRepository itemRepository) {
        this.sizesRepository = sizesRepository;
        this.itemRepository = itemRepository;
    }

    public Sizes addSizes(Sizes sizes){
        Item item = itemRepository.findById(sizes.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        try {
            sizes.setItem(item);
            sizesRepository.save(sizes);
        } catch (Exception e) {
            throw new RuntimeException("Error saving sizes: " + e.getMessage());
        }
        return sizes;
    }
}
