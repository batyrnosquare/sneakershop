package com.example.sneakershop.service;

import com.example.sneakershop.model.Item;
import com.example.sneakershop.model.Sizes;
import com.example.sneakershop.repository.ItemRepository;
import com.example.sneakershop.repository.SizesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SizesService {

    private final SizesRepository sizesRepository;

    private final ItemRepository itemRepository;


    public SizesService(SizesRepository sizesRepository, ItemRepository itemRepository) {
        this.sizesRepository = sizesRepository;
        this.itemRepository = itemRepository;
    }

    public Sizes addSizes(Sizes sizes){
        Optional<Sizes> existingSizes = sizesRepository.findByItem_IdAndSize(sizes.getItemId(), sizes.getSize());
        if (existingSizes.isPresent()){
            Sizes s = existingSizes.get();
            s.setQuantity(s.getQuantity() + sizes.getQuantity());
            return sizesRepository.save(s);
        } else {
            Item item = itemRepository.findById(sizes.getItem().getId()).orElseThrow(() -> new RuntimeException("Item not found"));
            sizes.setItem(item);
            return sizesRepository.save(sizes);
        }
    }
}
