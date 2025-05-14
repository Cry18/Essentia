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

import com.essentia.essentiaadministration.dto.ParfumerDto;
import com.essentia.essentiaadministration.service.impl.ParfumerServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/")
public class ParfumerController {

	private static final Logger logger = LogManager.getLogger(ParfumerController.class);
    	
	@Autowired
	 private ParfumerServiceImpl parfumerServiceImpl;

    @PostMapping("add/parfumer")
        public ParfumerDto addParfumer(@RequestBody @Valid ParfumerDto parfumer) {
			logger.debug("POST /add/parfumer - parfumer name: {}", parfumer.getName());
        	return parfumerServiceImpl.create(parfumer);
        }  

    @PutMapping("edit/parfumer/{id}")
    	public ParfumerDto editParfumer(@PathVariable int id, @RequestBody @Valid ParfumerDto parfumer) {
			logger.debug("PUT /edit/parfumer/ - parfumer id: {}, parfumer name: {}", id, parfumer.getName());
    		return parfumerServiceImpl.updateparfumer(id, parfumer);
        }

    @DeleteMapping("delete/parfumer/{id}")
    	public ParfumerDto deleteParfumer(@PathVariable int id) {
			logger.debug("DELETE /delete/parfumer/ - parfumer id: {}", id);
    		return parfumerServiceImpl.deleteById(id);
    	}
}
