package com.example.sneakershop.controller;

import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.service.ShoesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoes")
public class ShoesController {

    private final ShoesService shoesService;

    public ShoesController(ShoesService shoesService) {
        this.shoesService = shoesService;
    }

    @PostMapping("/save")
    public ResponseEntity<Shoes> saveShoes(@RequestBody Shoes shoes) {
        return ResponseEntity.status(201).body(shoesService.save(shoes));
    }

    @PostMapping("/get/{id}")
    public ResponseEntity<Shoes> getShoesById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Shoes shoes = shoesService.findById(id);
        return ResponseEntity.ok(shoes);
    }

    @PostMapping("/update")
    public Shoes updateShoes(Shoes shoes) {
        return shoesService.update(shoes);
    }

    @PostMapping("/delete/{id}")
    public void deleteShoes(@PathVariable Long id) {
        shoesService.delete(id);
    }

}
