package com.essentia.essentiauser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiauser.dto.ReviewDto;
import com.essentia.essentiauser.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/user/review/")
public class ReviewController {
   
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("create/")
    public ReviewDto createReview(@Valid @RequestBody ReviewDto reviewDto, 
                                @RequestParam(value = "userId") int userId, 
                                @RequestParam(value = "perfumeId") int perfumeId) {
        return userService.createReview(userId, perfumeId, reviewDto);
    }

    @DeleteMapping("delete/")
    public String deleteReview(@RequestParam(value = "reviewId") int reviewId, 
                            @RequestParam(value = "userId") int userId) {
         return userService.deleteReview(reviewId, userId);
    }

}
