package com.example.sneakershop.controller;

import com.example.sneakershop.model.Sizes;
import com.example.sneakershop.service.SizesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sizes")
public class SizesController {

    private final SizesService sizesService;


    public SizesController(SizesService sizesService) {
        this.sizesService = sizesService;
    }

    @PostMapping("/add")
    public Sizes addSize(@RequestBody Sizes size) {
        return sizesService.addSizes(size);
    }


}
