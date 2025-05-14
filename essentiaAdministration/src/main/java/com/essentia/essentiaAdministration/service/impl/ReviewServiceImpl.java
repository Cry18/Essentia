package com.essentia.essentiaadministration.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.entity.Review;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.ReviewRepository;
import com.essentia.essentiaadministration.service.ReviewService;



@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public int deleteById(int id) {
        logger.debug("Fetching review with id: {}",id);    
        Review review = reviewRepository.findById(id);
        if (review != null) {
            reviewRepository.delete(review);
            logger.info("Review with id: {} deleted",id);
        } else {
            logger.warn("Perfume not found with id: {}",id);
            throw new ResourceNotFoundException("Review not found");}
        return id;
}
}