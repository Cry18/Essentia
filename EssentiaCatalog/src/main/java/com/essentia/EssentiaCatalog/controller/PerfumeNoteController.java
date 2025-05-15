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

import com.essentia.essentiacatalog.dto.PerfumeNoteDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.PerfumeNoteServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class PerfumeNoteController {

    private static final Logger logger = LogManager.getLogger(PerfumeNoteController.class);

    @Autowired
	 private PerfumeNoteServiceImpl perfumeNoteService;

    @GetMapping("perfumenotes/")
    public List<PerfumeNoteDto> findByName(@RequestParam(value = "name", required = false) String name) {
        logger.debug("GET /perfumenotes/ - name: {}", name);
       if (name == null || name.isBlank()) return perfumeNoteService.findAllPerfumeNotes();
        List<PerfumeNoteDto> perfumeNotes = perfumeNoteService.findLikeNamePerfumeNotes(name);
        if (perfumeNotes.isEmpty()) {
            logger.warn("No perfume notes found with the name: {}", name);
            throw new ResourceNotFoundException("No perfume notes found");
        }
       return perfumeNotes;
   }
   
   @GetMapping("perfumenote/{id}")
   public PerfumeNoteDto perfumeNoteDetails(@PathVariable int id) {
        logger.debug("GET /perfumenote/{} - id: {}", id);
        return perfumeNoteService.details(id);
}
}
