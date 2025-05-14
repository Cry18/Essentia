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

import com.essentia.essentiacatalog.dto.ParfumerDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.ParfumerServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class ParfumerController {

    private static final Logger logger = LogManager.getLogger(ParfumerController.class);

    @Autowired
	 private ParfumerServiceImpl parfumerService;

    @GetMapping("parfumers/")
    public List<ParfumerDto> findByName(@RequestParam(value = "name", required = false) String name) {
        logger.debug("GET /parfumers/ - name: {}", name);
       if (name == null || name.isBlank()) return parfumerService.findAllParfumers();
        List<ParfumerDto> parfumers = parfumerService.findLikeNameParfumers(name);
        if (parfumers.isEmpty()) {
            logger.warn("No parfumers found with the name: {}", name);
            throw new ResourceNotFoundException("No parfumers found with the name: " + name);
        }
        return parfumers;
   }
   
   @GetMapping("parfumer/{id}")
   public ParfumerDto parfumerDetails(@PathVariable int id) {
        logger.debug("GET /parfumer/{} - id: {}", id);
        return parfumerService.details(id);
  }

}
