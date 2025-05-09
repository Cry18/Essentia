package com.essentia.essentiaadministration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaadministration.dto.BrandDto;
import com.essentia.essentiaadministration.service.impl.BrandServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class BrandController {
	
	@Autowired
	 private BrandServiceImpl brandService;	

    @PostMapping("add/brand")
     public BrandDto addBrand(@RequestBody @Valid BrandDto brand) {
        return brandService.create(brand);
    }

    @PutMapping("edit/brand/{id}")
     public BrandDto editBrand(@PathVariable int id, @RequestBody @Valid BrandDto brand) {
        return brandService.updateBrand(id, brand);
    }

    @DeleteMapping("delete/brand/{id}")
     public BrandDto deleteBrand(@PathVariable int id) {
        return brandService.deleteById(id);
    }
}
