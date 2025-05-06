package com.essentia.essentiaAdministration.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaAdministration.service.impl.ReviewServiceImpl;


@Validated
@RestController
@RequestMapping("/api/admin/")
public class ReviewController {

	@Autowired
	 private ReviewServiceImpl reviewServiceImpl;

    @DeleteMapping("delete/review/{id}")
    	public void deleteReview(@PathVariable int id) {
    		reviewServiceImpl.deleteById(id);
    	}
}
