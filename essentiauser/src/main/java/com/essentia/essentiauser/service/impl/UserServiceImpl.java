package com.essentia.essentiauser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiauser.dto.ReviewDto;
import com.essentia.essentiauser.entity.Perfume;
import com.essentia.essentiauser.entity.Review;
import com.essentia.essentiauser.entity.User;
import com.essentia.essentiauser.exception.DuplicateReviewException;
import com.essentia.essentiauser.exception.ForbiddenActionException;
import com.essentia.essentiauser.exception.ResourceNotFoundException;
import com.essentia.essentiauser.repository.PerfumeRepository;
import com.essentia.essentiauser.repository.ReviewRepository;
import com.essentia.essentiauser.repository.UserRepository;
import com.essentia.essentiauser.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PerfumeRepository perfumeRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public String addPerfumeToFavorites(int userId, int perfumeId) {
            User user = userRepository.findById(userId);
                if (user == null) {
                    throw new ResourceNotFoundException("User not found");
                }

            Perfume perfume = perfumeRepository.findById(perfumeId);
                if (perfume == null) {
                    throw new ResourceNotFoundException("Perfume not found");
                }
            if (user.getFavorites().contains(perfume)) {
                throw new ResourceNotFoundException("Perfume already in favorites");
            }
            user.addFavorite(perfume);
            userRepository.save(user);
            return perfume.getName() + " added to favorites";
        }

    @Override
    public String removePerfumeFromFavorites(int userId, int perfumeId) {
        User user = userRepository.findById(userId);
                if (user == null) {
                    throw new ResourceNotFoundException("User not found");
                }

        Perfume perfume = perfumeRepository.findById(perfumeId);
                if (perfume == null) {
                    throw new ResourceNotFoundException("Perfume not found");
                }

        if (user.removeFavorite(perfume)) {
            userRepository.save(user);
            return perfume.getName() + " removed from favorites";
        } else throw new ResourceNotFoundException("There is no perfume with id: " + perfumeId + " in user's favorites with id: " + userId);
    }
    @Override
    public String setSignature(int userId, int perfumeId){
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }	
        Perfume perfume = perfumeRepository.findById(perfumeId);
        if (perfume == null) {
            throw new ResourceNotFoundException("Perfume not found");
        }
        user.setSignature(perfume);
		userRepository.save(user);
        return perfume.getName() + " set as signature for user with id : " + userId;
    }

    @Override
    public ReviewDto createReview(int userId, int perfumeId, ReviewDto review){
        if (reviewRepository.findByUserId(userId) != null) {
            throw new DuplicateReviewException("User already has a review for this perfume");
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        Perfume perfume = perfumeRepository.findById(perfumeId);
        if (perfume == null) {
            throw new ResourceNotFoundException("Perfume not found");
        }
        Review reviewEntity = new Review(perfume, user, review.getTitle(), review.getDescription(), review.getVote(), review.getSeasonality(), review.isGender(), review.getSillage(), review.getLongevity());
        reviewRepository.save(reviewEntity);
        review.setId(reviewEntity.getId());
        return review;
    }

    @Override
    public String deleteReview(int reviewId, int userId) {
        Review review = reviewRepository.findById(reviewId);
        if (review != null) {
            if (review.getUser().getId() != userId) {
                throw new ForbiddenActionException("This review does not belong to the user with id: " + userId);
            } else {
            reviewRepository.delete(review);
            return "Review with id: " + reviewId + " deleted";}
        } else throw new ResourceNotFoundException("There is no review with id: " + reviewId + " for user with id: " + userId);
    }


}
