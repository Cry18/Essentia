package com.essentia.essentiaAdministration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaAdministration.dto.PerfumeDto;
import com.essentia.essentiaAdministration.service.impl.PerfumeServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class PerfumeController {
    @Autowired
    private PerfumeServiceImpl perfumeService;

    // Create
    @PostMapping("add/perfume")
    public void addPerfume(@RequestBody @Valid PerfumeDto perfume) {
        perfumeService.create(perfume);
    }

    // Update
    @PutMapping("edit/perfume/{id}")
    public void editPerfume(@PathVariable int id, @RequestBody @Valid PerfumeDto perfume) {
        perfumeService.updatePerfume(id, perfume);
    }
    
    // Delete
    @DeleteMapping("delete/perfume/{id}")
    public void deletePerfume(@PathVariable int id) {
        perfumeService.deleteById(id);
    }
}
