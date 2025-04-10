package com.example.sneakershop.controller;

import com.example.sneakershop.model.FilterShoes;
import com.example.sneakershop.model.Shoes;
import com.example.sneakershop.service.ShoesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoes")
public class ShoesController {

    private final ShoesService shoesService;

    public ShoesController(ShoesService shoesService) {
        this.shoesService = shoesService;
    }

    @PostMapping("/save")
    public Shoes saveShoes(@RequestBody Shoes shoes) {
        return shoesService.save(shoes);
    }

    @PostMapping("/get/{id}")
    public Shoes getShoesById(@PathVariable Long id) {
        return shoesService.findById(id);
    }

    @PostMapping("/get_all")
    public List<Shoes> getAllShoes(
            @RequestBody FilterShoes filter,
            @RequestParam String sortBy) {
        return shoesService.findAll(filter, sortBy);
    }


    @PostMapping("/update{id}")
    public Shoes updateShoes(@PathVariable Long id, @RequestBody Shoes shoes) {
        return shoesService.update(id, shoes);
    }


    @PostMapping("/delete/{id}")
    public void deleteShoes(@PathVariable Long id) {
        shoesService.delete(id);
    }


}
