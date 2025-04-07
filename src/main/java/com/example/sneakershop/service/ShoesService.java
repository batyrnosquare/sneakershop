package com.example.sneakershop.service;

import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.model.User;
import com.example.sneakershop.repository.ShoesRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoesService {

    private final ShoesRepository shoesRepository;

    public ShoesService(ShoesRepository shoesRepository) {
        this.shoesRepository = shoesRepository;
    }

    public Shoes save(Shoes shoes) {
        shoesRepository.save(shoes);
        return shoes;
    }

    public Shoes findById(Long id) {
        return shoesRepository.findById(id).orElse(null);
    }

    public Shoes update(Shoes shoes) {
        return shoesRepository.save(shoes);
    }

    public void delete(Long id) {
        shoesRepository.deleteById(id);
    }
}
