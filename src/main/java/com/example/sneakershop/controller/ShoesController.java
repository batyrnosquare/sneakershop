package com.example.sneakershop.controller;

import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoes")
public class ShoesController {

    @Autowired
    private ShoesService shoesService;

    @PostMapping("/save")
    public void saveShoes(Shoes shoes) {
        shoesService.save(shoes);
    }

    @PostMapping("/get/{id}")
    public Shoes getShoesById(Long id) {
        return shoesService.findById(id);
    }

    @PostMapping("/update")
    public Shoes updateShoes(Shoes shoes) {
        return shoesService.update(shoes);
    }

    @PostMapping("/delete/{id}")
    public void deleteShoes(Long id) {
        shoesService.delete(id);
    }

}
