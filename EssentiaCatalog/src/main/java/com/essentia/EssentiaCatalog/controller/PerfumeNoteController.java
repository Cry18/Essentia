package com.essentia.essentiacatalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiacatalog.dto.PerfumeNoteDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.PerfumeNoteServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class PerfumeNoteController {
    @Autowired
	 private PerfumeNoteServiceImpl perfumeNoteService;

    @GetMapping("perfumenotes/")
    public List<PerfumeNoteDto> findByName(@RequestParam(value = "name", required = false) String name) {
       if (name == null || name.isBlank()) return perfumeNoteService.findAllPerfumeNotes();
        List<PerfumeNoteDto> perfumeNotes = perfumeNoteService.findLikeNamePerfumeNotes(name);
        if (perfumeNotes.isEmpty()) {
            throw new ResourceNotFoundException("No perfume notes found with the name: " + name);
        }
       return perfumeNotes;
   }
   
   @GetMapping("perfumenote/{id}")
   public PerfumeNoteDto perfumeNoteDetails(@PathVariable int id) {
      return perfumeNoteService.details(id);
}
}
