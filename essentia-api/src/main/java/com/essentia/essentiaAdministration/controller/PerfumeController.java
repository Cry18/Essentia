package com.essentia.essentiaAdministration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("edit/perfume/{id}")
    public void editPerfume(@PathVariable int id, @RequestBody @Valid PerfumeDto perfume) {
        perfumeService.updatePerfume(id, perfume);
    }
    
    // Delete
    @PostMapping("delete/perfume/{id}")
    public void deletePerfume(@PathVariable int id) {
        perfumeService.deleteById(id);
    }
}
