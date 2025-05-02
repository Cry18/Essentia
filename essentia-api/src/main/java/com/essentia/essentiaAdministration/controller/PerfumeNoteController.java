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

import com.essentia.essentiaAdministration.dto.PerfumeNoteDto;
import com.essentia.essentiaAdministration.service.impl.PerfumeNoteServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class PerfumeNoteController {
	
	@Autowired
	 private PerfumeNoteServiceImpl perfumeNoteService;

	//Create
	@PostMapping("add/perfumenote")
	    public void addPerfumeNote(@RequestBody @Valid PerfumeNoteDto prfNote) {
			perfumeNoteService.create(prfNote);
	    }
	
	//Update
	@PutMapping("edit/perfumenote/{id}")
		public void editPerfumeNote(@PathVariable int id, @RequestBody @Valid PerfumeNoteDto prfNote) {
			perfumeNoteService.updatePerfumeNote(id, prfNote);
	    }
	
	//Delete
	@DeleteMapping("delete/perfumenote/{id}")
		public void deletePerfumeNote(@PathVariable int id) {
			perfumeNoteService.deleteById(id);
		}
}
