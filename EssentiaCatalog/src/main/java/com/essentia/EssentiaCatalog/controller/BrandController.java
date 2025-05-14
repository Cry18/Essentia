package com.essentia.essentiacatalog.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    private static final Logger logger = LogManager.getLogger(BrandController.class);


	@Autowired
	 private BrandServiceImpl brandService;

    @GetMapping("brands/")
     public List<BrandDto> findByName(@RequestParam(value = "name", required = false) String name) {
        logger.debug("GET /brands/ - name: {}", name);
        if (name == null || name.isBlank()) return brandService.findAllBrands();
        List<BrandDto> brands = brandService.findLikeNameBrands(name);
        if (brands.isEmpty()) {
            logger.warn("No brands found with the name: {}", name);
            throw new ResourceNotFoundException("No brands found with the name: " + name);
        }
        return brands;
    }

    @GetMapping("brand/{id}")
     public BrandDto brandDetails(@PathVariable int id) {
        logger.debug("GET /brand/{} - id: {}", id);
        return brandService.details(id);
    }

}
