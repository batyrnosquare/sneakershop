package com.example.sneakershop.service;

import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoesService {

    @Autowired
    private ShoesRepository shoesRepository;

    public Shoes save(Shoes shoes) {
        shoesRepository.save(shoes);
        return shoes;
    }

    public Shoes findById(Long id) {
        Shoes shoes = shoesRepository.findById(id).orElse(null);
        return shoes;
    }

    public Shoes update(Shoes shoes) {
        return shoesRepository.save(shoes);
    }

    public void delete(Long id) {
        shoesRepository.deleteById(id);
    }
}
