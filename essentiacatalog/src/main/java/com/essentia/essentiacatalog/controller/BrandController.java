package com.essentia.essentiacatalog.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.common.dto.BrandDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.BrandServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class BrandController {

    private static final Logger logger = LogManager.getLogger(BrandController.class);

    @Autowired
    private BrandServiceImpl brandService;

    /**
     * Returns a paginated list of brands, optionally filtered by name.
     *
     * @param page zero-based page index (default: 0)
     * @param size number of items per page (default: 20)
     */
    @GetMapping("brands/")
    public Page<BrandDto> findByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        logger.debug("GET /brands/ - name: {}, page: {}, size: {}", name, page, size);

        if (name == null || name.isBlank()) {
            return brandService.findAllBrands(pageable);
        }

        Page<BrandDto> result = brandService.findLikeNameBrands(name, pageable);
        if (!result.hasContent()) {
            logger.warn("No brands found with name: {}", name);
            throw new ResourceNotFoundException("No brands found");
        }
        return result;
    }

    @GetMapping("brand/{id}")
    public BrandDto brandDetails(@PathVariable int id) {
        logger.debug("GET /brand/{}", id);
        return brandService.details(id);
    }
}
