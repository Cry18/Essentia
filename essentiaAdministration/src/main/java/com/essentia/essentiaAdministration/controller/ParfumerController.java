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

import com.essentia.essentiaadministration.dto.ParfumerDto;
import com.essentia.essentiaadministration.service.impl.ParfumerServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class ParfumerController {
    	
	@Autowired
	 private ParfumerServiceImpl parfumerServiceImpl;

    @PostMapping("add/parfumer")
        public ParfumerDto addParfumer(@RequestBody @Valid ParfumerDto parfumer) {
        	return parfumerServiceImpl.create(parfumer);
        }  

    @PutMapping("edit/parfumer/{id}")
    	public ParfumerDto editParfumer(@PathVariable int id, @RequestBody @Valid ParfumerDto parfumer) {
    		return parfumerServiceImpl.updateparfumer(id, parfumer);
        }

    @DeleteMapping("delete/parfumer/{id}")
    	public ParfumerDto deleteParfumer(@PathVariable int id) {
    		return parfumerServiceImpl.deleteById(id);
    	}
}
