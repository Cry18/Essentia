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

import com.essentia.essentiaadministration.dto.PerfumeNoteDto;
import com.essentia.essentiaadministration.service.impl.PerfumeNoteServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class PerfumeNoteController {

	private static final Logger logger = LogManager.getLogger(PerfumeNoteController.class);
	
	@Autowired
	 private PerfumeNoteServiceImpl perfumeNoteService;

	@PostMapping("add/perfumenote")
	    public PerfumeNoteDto addPerfumeNote(@RequestBody @Valid PerfumeNoteDto prfNote) {
			logger.debug("POST /add/perfumenote - perfume note name: {}", prfNote.getName());
			return perfumeNoteService.create(prfNote);
	    }
	
	@PutMapping("edit/perfumenote/{id}")
		public PerfumeNoteDto editPerfumeNote(@PathVariable int id, @RequestBody @Valid PerfumeNoteDto prfNote) {
			logger.debug("PUT /edit/perfumenote/ - perfume note id: {}, perfume note name: {}", id, prfNote.getName());
			return perfumeNoteService.updatePerfumeNote(id, prfNote);
	    }
	
	@DeleteMapping("delete/perfumenote/{id}")
		public PerfumeNoteDto deletePerfumeNote(@PathVariable int id) {
			logger.debug("DELETE /delete/perfumenote/ - perfume note id: {}", id);
			return perfumeNoteService.deleteById(id);
		}
}
