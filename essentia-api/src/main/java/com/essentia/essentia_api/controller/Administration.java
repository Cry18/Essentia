package com.essentia.essentia_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentia_api.dto.PerfumeNoteDto;
import com.essentia.essentia_api.service.impl.PerfumeNoteServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class Administration {
	
	@Autowired
	 private PerfumeNoteServiceImpl perfume_noteService;

	
	 @PostMapping("add/perfume_note")
	    public void addPerfumeNote(@RequestBody @Valid PerfumeNoteDto prfNote) {
		 perfume_noteService.create(prfNote);
	    }
	 
	 //TEST
	 @GetMapping("all")
	 public List<PerfumeNoteDto> allNotes() {
		 return perfume_noteService.findAll();
	    }
}
