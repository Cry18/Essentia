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

import com.essentia.essentiaadministration.dto.PerfumeDto;
import com.essentia.essentiaadministration.service.impl.PerfumeServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class PerfumeController {

    private static final Logger logger = LogManager.getLogger(PerfumeController.class);

    @Autowired
    private PerfumeServiceImpl perfumeService;

    @PostMapping("add/perfume")
    public PerfumeDto addPerfume(@RequestBody @Valid PerfumeDto perfume) {
        logger.debug("POST /add/perfume - perfume name: {}", perfume.getName());
        return perfumeService.create(perfume);
    }

    @PutMapping("edit/perfume/{id}")
    public PerfumeDto editPerfume(@PathVariable int id, @RequestBody @Valid PerfumeDto perfume) {
        logger.debug("PUT /edit/perfume/ - perfume id: {}, perfume name: {}", id, perfume.getName());
        return perfumeService.updatePerfume(id, perfume);
    }
    
    @DeleteMapping("delete/perfume/{id}")
    public PerfumeDto deletePerfume(@PathVariable int id) {
        logger.debug("DELETE /delete/perfume/ - perfume id: {}", id);
        return perfumeService.deleteById(id);
    }
}
