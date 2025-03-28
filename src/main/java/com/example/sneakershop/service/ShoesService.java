package com.example.sneakershop.service;

import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.repository.ShoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoesService {

    @Autowired
    private ShoesRepository shoesRepository;

    public void save(Shoes shoes) {
        shoesRepository.save(shoes);
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
