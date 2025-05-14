package com.essentia.essentiaadministration.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(BrandController.class);

	
	@Autowired
	 private BrandServiceImpl brandService;	

    @PostMapping("add/brand")
     public BrandDto addBrand(@RequestBody @Valid BrandDto brand) {
        logger.debug("POST /add/brand - brand name: {}", brand.getName());
        return brandService.create(brand);
    }

    @PutMapping("edit/brand/{id}")
     public BrandDto editBrand(@PathVariable int id, @RequestBody @Valid BrandDto brand) {
        logger.debug("PUT /edit/brand/ - brand id: {}, brand name: {}", id, brand.getName());
        return brandService.updateBrand(id, brand);
    }

    @DeleteMapping("delete/brand/{id}")
     public BrandDto deleteBrand(@PathVariable int id) {
        logger.debug("DELETE /delete/brand/ - brand id: {}", id);
        return brandService.deleteById(id);
    }
}
