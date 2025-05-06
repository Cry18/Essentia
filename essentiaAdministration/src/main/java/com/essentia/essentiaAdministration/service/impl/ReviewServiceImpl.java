package com.essentia.essentiaAdministration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaAdministration.entity.Review;
import com.essentia.essentiaAdministration.repository.ReviewRepository;
import com.essentia.essentiaAdministration.service.ReviewService;



@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void deleteById(int id) {    
        Review review = reviewRepository.findById(id);
        if (review != null) {
            reviewRepository.delete(review);
        }
}
}