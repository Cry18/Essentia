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

import com.essentia.common.dto.PerfumeNoteDto;
import com.essentia.essentiacatalog.exception.ResourceNotFoundException;
import com.essentia.essentiacatalog.service.impl.PerfumeNoteServiceImpl;

@RestController
@RequestMapping("/api/catalog/")
public class PerfumeNoteController {

    private static final Logger logger = LogManager.getLogger(PerfumeNoteController.class);

    @Autowired
    private PerfumeNoteServiceImpl perfumeNoteService;

    /**
     * Returns a paginated list of fragrance notes, optionally filtered by name.
     *
     * @param page zero-based page index (default: 0)
     * @param size number of items per page (default: 20)
     */
    @GetMapping("perfumenotes/")
    public Page<PerfumeNoteDto> findByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());

        logger.debug("GET /perfumenotes/ - name: {}, page: {}, size: {}", name, page, size);

        if (name == null || name.isBlank()) {
            return perfumeNoteService.findAllPerfumeNotes(pageable);
        }

        Page<PerfumeNoteDto> result = perfumeNoteService.findLikeNamePerfumeNotes(name, pageable);
        if (!result.hasContent()) {
            logger.warn("No perfume notes found with name: {}", name);
            throw new ResourceNotFoundException("No perfume notes found");
        }
        return result;
    }

    @GetMapping("perfumenote/{id}")
    public PerfumeNoteDto perfumeNoteDetails(@PathVariable int id) {
        logger.debug("GET /perfumenote/{}", id);
        return perfumeNoteService.details(id);
    }
}
