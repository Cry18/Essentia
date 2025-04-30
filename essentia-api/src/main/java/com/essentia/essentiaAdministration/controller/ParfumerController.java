package com.essentia.essentiaAdministration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaAdministration.dto.ParfumerDto;
import com.essentia.essentiaAdministration.service.impl.ParfumerServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class ParfumerController {
    	
	@Autowired
	 private ParfumerServiceImpl parfumerServiceImpl;

    //Create
    @PostMapping("add/parfumer")
        public void addParfumer(@RequestBody @Valid ParfumerDto parfumer) {
        	parfumerServiceImpl.create(parfumer);
        }  

    //Update
    @PostMapping("edit/parfumer/{id}")
    	public void editParfumer(@PathVariable int id, @RequestBody @Valid ParfumerDto parfumer) {
    		parfumerServiceImpl.updateparfumer(id, parfumer);
        }
    //Delete
    @PostMapping("delete/parfumer/{id}")
    	public void deleteParfumer(@PathVariable int id) {
    		parfumerServiceImpl.deleteById(id);
    	}
}
