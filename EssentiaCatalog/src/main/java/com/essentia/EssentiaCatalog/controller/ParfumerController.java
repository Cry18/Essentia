package com.essentia.EssentiaCatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.EssentiaCatalog.dto.ParfumerDto;
import com.essentia.EssentiaCatalog.exception.ResourceNotFoundException;
import com.essentia.EssentiaCatalog.service.impl.ParfumerServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class ParfumerController {

    @Autowired
	 private ParfumerServiceImpl parfumerService;

    @GetMapping("parfumers/")
    public List<String> findByName(@RequestParam(value = "name", required = false) String name) {
       if (name == null || name.isBlank()) return parfumerService.findAllParfumers();
        List<String> parfumers = parfumerService.findLikeNameParfumers(name);
        if (parfumers.isEmpty()) {
            throw new ResourceNotFoundException("No parfumers found with the name: " + name);
        }
       return parfumers;
   }
   
   @GetMapping("parfumer/{id}")
   public ParfumerDto parfumerDetails(@PathVariable int id) {
      return parfumerService.details(id);
  }

}
