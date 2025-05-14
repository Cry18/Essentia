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

import com.essentia.essentiacatalog.dto.PerfumeDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.PerfumeServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class PerfumeController {

    private static final Logger logger = LogManager.getLogger(PerfumeController.class);
    
    @Autowired
	 private PerfumeServiceImpl perfumeService;

     @GetMapping("perfumes")
     public List<PerfumeDto> findByFilters( 
             @RequestParam(value = "name", required = false) String name,
             @RequestParam(value = "parfumer", required = false) String parfumer,
             @RequestParam(value = "brand", required = false) String brand,
             @RequestParam(value = "note", required = false) String note) {

            logger.debug("GET /perfumes - name: {}, parfumer: {}, brand: {}, note: {}", name, parfumer, brand, note);

            if ((name == null || name.isBlank()) &&
             (parfumer == null || parfumer.isBlank()) &&
             (brand == null || brand.isBlank()) &&
             (note == null || note.isBlank())) {
             return perfumeService.findAllPerfumes();
         }
     
         List<PerfumeDto> perfumes = perfumeService.findPerfumesByFilters(name, parfumer, brand, note);
         if (perfumes.isEmpty()) {
            logger.warn("No perfumes found with the given filters: name: {}, parfumer: {}, brand: {}, note: {}", name, parfumer, brand, note);
            throw new ResourceNotFoundException("No perfumes found with the given filters.");
         }
         return perfumes;
     } 
   
   @GetMapping("perfume/{id}")
   public PerfumeDto perfumeDetails(@PathVariable int id) {
        logger.debug("GET /perfume/{} - id: {}", id);
      return perfumeService.details(id);
  }
}
