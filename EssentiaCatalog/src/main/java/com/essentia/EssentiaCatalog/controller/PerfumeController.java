package com.essentia.EssentiaCatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.EssentiaCatalog.dto.PerfumeDto;
import com.essentia.EssentiaCatalog.exception.ResourceNotFoundException;
import com.essentia.EssentiaCatalog.service.impl.PerfumeServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class PerfumeController {
    
    @Autowired
	 private PerfumeServiceImpl perfumeService;

     @GetMapping("perfumes")
     public List<PerfumeDto> findByFilters( 
             @RequestParam(value = "name", required = false) String name,
             @RequestParam(value = "parfumer", required = false) String parfumer,
             @RequestParam(value = "brand", required = false) String brand,
             @RequestParam(value = "note", required = false) String note) {
     
            if ((name == null || name.isBlank()) &&
             (parfumer == null || parfumer.isBlank()) &&
             (brand == null || brand.isBlank()) &&
             (note == null || note.isBlank())) {
             return perfumeService.findAllPerfumes();
         }
     
         List<PerfumeDto> perfumes = perfumeService.findPerfumesByFilters(name, parfumer, brand, note);
         if (perfumes.isEmpty()) {
             throw new ResourceNotFoundException("No perfumes found with the given filters.");
         }
         return perfumes;
     } 
   
   @GetMapping("perfume/{id}")
   public PerfumeDto perfumeDetails(@PathVariable int id) {
      return perfumeService.details(id);
  }
}
