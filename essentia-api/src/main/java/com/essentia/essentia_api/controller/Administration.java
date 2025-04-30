package com.essentia.essentia_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentia_api.dto.Perfume_noteDto;
import com.essentia.essentia_api.service.impl.Perfume_noteServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class Administration {
	
	@Autowired
	 private Perfume_noteServiceImpl perfume_noteService;

	
	 @PostMapping("add/perfume-note")
	    public void addPerfumeNote(@RequestBody @Valid Perfume_noteDto prfNote) {
		 perfume_noteService.create(prfNote);
	    }
	 
	 @GetMapping("all")
	 public Perfume_noteDto allNotes() {
		 //Lista di perfume_note
		 perfume_noteService.findAll();
		 return (Perfume_noteDto);
	    }
}
