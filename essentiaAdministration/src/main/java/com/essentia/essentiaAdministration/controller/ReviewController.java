package com.essentia.essentiaadministration.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaadministration.service.impl.ReviewServiceImpl;


@Validated
@RestController
@RequestMapping("/api/admin/")
public class ReviewController {

	@Autowired
	 private ReviewServiceImpl reviewServiceImpl;

    @DeleteMapping("delete/review/{id}")
    	public int deleteReview(@PathVariable int id) {
    		return reviewServiceImpl.deleteById(id);
    	}
}
