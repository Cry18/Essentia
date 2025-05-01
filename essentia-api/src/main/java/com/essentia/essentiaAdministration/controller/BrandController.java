package com.essentia.essentiaAdministration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaAdministration.dto.BrandDto;
import com.essentia.essentiaAdministration.service.impl.BrandServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class BrandController {
	
	@Autowired
	 private BrandServiceImpl brandService;	

	
	//Create
    @PostMapping("add/brand")
     public void addBrand(@RequestBody @Valid BrandDto brand) {
        brandService.create(brand);
    }

    //Update
    @PostMapping("edit/brand/{id}")
     public void editBrand(@PathVariable int id, @RequestBody @Valid BrandDto brand) {
        brandService.updateBrand(id, brand);
    }

    //Delete
    @PostMapping("delete/brand/{id}")
     public void deleteBrand(@PathVariable int id) {
        brandService.deleteById(id);
    }
	 
	//TEST
	@GetMapping("all/brands")
	 public List<BrandDto> allBrands() {
		 return brandService.findAll();
	    }
}
