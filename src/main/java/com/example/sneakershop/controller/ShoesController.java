package com.example.sneakershop.controller;

import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoes")
public class ShoesController {

    @Autowired
    private ShoesService shoesService;

    @PostMapping("/save")
    public ResponseEntity<Shoes> saveShoes(@RequestBody Shoes shoes) {
        if (shoes == null || shoes.getName() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Shoes savedShoes = shoesService.save(shoes);
        return ResponseEntity.ok(savedShoes);

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
