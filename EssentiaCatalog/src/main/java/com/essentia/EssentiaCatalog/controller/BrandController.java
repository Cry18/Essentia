package com.essentia.essentiacatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiacatalog.dto.BrandDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.BrandServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class BrandController {
	
	@Autowired
	 private BrandServiceImpl brandService;

    @GetMapping("brands/")
     public List<BrandDto> findByName(@RequestParam(value = "name", required = false) String name) {
        if (name == null || name.isBlank()) return brandService.findAllBrands();
        List<BrandDto> brands = brandService.findLikeNameBrands(name);
        if (brands.isEmpty()) {
            throw new ResourceNotFoundException("No brands found with the name: " + name);
        }
        return brands;
    }

    @GetMapping("brand/{id}")
     public BrandDto brandDetails(@PathVariable int id) {
        return brandService.details(id);
    }

}
