package com.example.sneakershop.model;

import com.example.sneakershop.constants.Material;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterShoes {

    private List<String> brand;
    private List<String> color;
    private List<Material> material;
    private List<Integer> size;
    private Double priceFrom;
    private Double priceTo;

}
