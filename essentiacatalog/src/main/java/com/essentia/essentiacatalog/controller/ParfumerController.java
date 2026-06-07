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

import com.essentia.common.dto.ParfumerDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.ParfumerServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class ParfumerController {

    private static final Logger logger = LogManager.getLogger(ParfumerController.class);

    @Autowired
    private ParfumerServiceImpl parfumerService;

    /**
     * Returns a paginated list of parfumers, optionally filtered by name.
     *
     * @param page zero-based page index (default: 0)
     * @param size number of items per page (default: 20)
     */
    @GetMapping("parfumers/")
    public Page<ParfumerDto> findByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        logger.debug("GET /parfumers/ - name: {}, page: {}, size: {}", name, page, size);

        if (name == null || name.isBlank()) {
            return parfumerService.findAllParfumers(pageable);
        }

        Page<ParfumerDto> result = parfumerService.findLikeNameParfumers(name, pageable);
        if (!result.hasContent()) {
            logger.warn("No parfumers found with name: {}", name);
            throw new ResourceNotFoundException("No parfumers found");
        }
        return result;
    }

    @GetMapping("parfumer/{id}")
    public ParfumerDto parfumerDetails(@PathVariable int id) {
        logger.debug("GET /parfumer/{}", id);
        return parfumerService.details(id);
    }
}
