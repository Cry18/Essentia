package com.essentia.essentiaadministration.controller;

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
	
	@Autowired
	 private PerfumeNoteServiceImpl perfumeNoteService;

	@PostMapping("add/perfumenote")
	    public PerfumeNoteDto addPerfumeNote(@RequestBody @Valid PerfumeNoteDto prfNote) {
			return perfumeNoteService.create(prfNote);
	    }
	
	@PutMapping("edit/perfumenote/{id}")
		public PerfumeNoteDto editPerfumeNote(@PathVariable int id, @RequestBody @Valid PerfumeNoteDto prfNote) {
			return perfumeNoteService.updatePerfumeNote(id, prfNote);
	    }
	
	@DeleteMapping("delete/perfumenote/{id}")
		public PerfumeNoteDto deletePerfumeNote(@PathVariable int id) {
			return perfumeNoteService.deleteById(id);
		}
}
