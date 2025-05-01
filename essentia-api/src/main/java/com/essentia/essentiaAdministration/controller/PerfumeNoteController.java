package com.essentia.essentiaAdministration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping("add/perfumeNote")
	    public void addPerfumeNote(@RequestBody @Valid PerfumeNoteDto prfNote) {
			perfumeNoteService.create(prfNote);
	    }
	
	//Update
	@PostMapping("edit/perfume-note/{id}")
		public void editPerfumeNote(@PathVariable int id, @RequestBody @Valid PerfumeNoteDto prfNote) {
			perfumeNoteService.updatePerfumeNote(id, prfNote);
	    }
	
	//Delete
	@PostMapping("delete/perfume-note/{id}")
		public void deletePerfumeNote(@PathVariable int id) {
			perfumeNoteService.deleteById(id);
		}
	 
	//TEST
	@GetMapping("all/notes")
		public List<PerfumeNoteDto> allNotes() {
		 return perfumeNoteService.findAll();
	    }
}
