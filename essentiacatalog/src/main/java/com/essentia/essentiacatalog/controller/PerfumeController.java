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

import com.essentia.essentiacatalog.dto.PerfumeDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.PerfumeServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class PerfumeController {

    private static final Logger logger = LogManager.getLogger(PerfumeController.class);

    @Autowired
    private PerfumeServiceImpl perfumeService;

    /**
     * Returns a paginated list of perfumes, optionally filtered by name, parfumer, brand or note.
     *
     * @param page zero-based page index (default: 0)
     * @param size number of items per page (default: 20)
     */
    @GetMapping("perfumes")
    public Page<PerfumeDto> findByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String parfumer,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String note,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        logger.debug("GET /perfumes - name: {}, parfumer: {}, brand: {}, note: {}, page: {}, size: {}",
                name, parfumer, brand, note, page, size);

        boolean noFilters = (name == null || name.isBlank())
                && (parfumer == null || parfumer.isBlank())
                && (brand == null || brand.isBlank())
                && (note == null || note.isBlank());

        if (noFilters) {
            return perfumeService.findAllPerfumes(pageable);
        }

        Page<PerfumeDto> result = perfumeService.findPerfumesByFilters(name, parfumer, brand, note, pageable);
        if (!result.hasContent()) {
            logger.warn("No perfumes found for filters - name: {}, parfumer: {}, brand: {}, note: {}",
                    name, parfumer, brand, note);
            throw new ResourceNotFoundException("No perfumes found with the given filters.");
        }
        return result;
    }

    @GetMapping("perfume/{id}")
    public PerfumeDto perfumeDetails(@PathVariable int id) {
        logger.debug("GET /perfume/{}", id);
        return perfumeService.details(id);
    }
}
