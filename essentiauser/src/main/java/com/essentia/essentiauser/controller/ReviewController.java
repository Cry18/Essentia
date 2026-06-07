package com.essentia.essentiauser.controller;

import com.essentia.essentiauser.dto.ReviewDto;
import com.essentia.essentiauser.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/user/review/")
public class ReviewController {

    private static final Logger logger = LogManager.getLogger(ReviewController.class);

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("create/")
    public ReviewDto createReview(@Valid @RequestBody ReviewDto reviewDto,
                                  @RequestParam(value = "perfumeId") int perfumeId) {
        int userId = getCurrentUserId();
        logger.debug("POST /review/create/ - userId: {} perfumeId: {}", userId, perfumeId);
        return userService.createReview(userId, perfumeId, reviewDto);
    }

    @PutMapping("update/")
    public String updateReview(@Valid @RequestBody ReviewDto reviewDto,
                               @RequestParam(value = "reviewId") int reviewId) {
        int userId = getCurrentUserId();
        logger.debug("PUT /review/update/ - reviewId: {} userId: {}", reviewId, userId);
        return userService.updateReview(reviewId, userId, reviewDto);
    }

    @DeleteMapping("delete/")
    public String deleteReview(@RequestParam(value = "reviewId") int reviewId) {
        int userId = getCurrentUserId();
        logger.debug("DELETE /review/delete/ - reviewId: {} userId: {}", reviewId, userId);
        return userService.deleteReview(reviewId, userId);
    }

    private int getCurrentUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
