package com.essentia.essentiauser.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ReviewController.class);

   
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("create/")
    public ReviewDto createReview(@Valid @RequestBody ReviewDto reviewDto, 
                                @RequestParam(value = "userId") int userId, 
                                @RequestParam(value = "perfumeId") int perfumeId) {
        logger.debug("POST /review/create/ - userId: {} perfumeId: {}", userId, perfumeId);
        return userService.createReview(userId, perfumeId, reviewDto);
    }

    @DeleteMapping("delete/")
    public String deleteReview(@RequestParam(value = "reviewId") int reviewId, 
                            @RequestParam(value = "userId") int userId) {
        logger.debug("DELETE /review/delete/ - reviewId: {} userId: {}", reviewId, userId);
         return userService.deleteReview(reviewId, userId);
    }

}
