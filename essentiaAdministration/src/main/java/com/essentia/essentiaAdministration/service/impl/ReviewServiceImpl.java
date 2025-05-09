package com.essentia.essentiaadministration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.entity.Review;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.ReviewRepository;
import com.essentia.essentiaadministration.service.ReviewService;



@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public int deleteById(int id) {    
        Review review = reviewRepository.findById(id);
        if (review != null) {
            reviewRepository.delete(review);
        } else throw new ResourceNotFoundException("Review not found with id: " + id);
        return id;
}
}