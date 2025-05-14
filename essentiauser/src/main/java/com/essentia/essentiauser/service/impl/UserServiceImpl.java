package com.essentia.essentiauser.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PerfumeRepository perfumeRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public String addPerfumeToFavorites(int userId, int perfumeId) {
        logger.debug("Fetching user with id: {}", userId);
            User user = userRepository.findById(userId);
                if (user == null) {
                    logger.debug("User not found with id: {}", userId);
                    throw new ResourceNotFoundException("User not found");
                }
            logger.debug("Fetching perfume with id: {}", perfumeId);
            Perfume perfume = perfumeRepository.findById(perfumeId);
                if (perfume == null) {
                    logger.debug("Perfume not found with id: {}", perfumeId);
                    throw new ResourceNotFoundException("Perfume not found");
                }
            if (user.getFavorites().contains(perfume)) {
                logger.debug("Perfume with id: {} already in favorites", perfumeId);
                throw new ResourceNotFoundException("Perfume already in favorites");
            }
            user.addFavorite(perfume);
            userRepository.save(user);
            logger.debug("Perfume with id: {} added to favorites", perfumeId);
            return perfume.getName() + " added to favorites";
        }

    @Override
    public String removePerfumeFromFavorites(int userId, int perfumeId) {
        logger.debug("Fetching user with id: {}", userId);
        User user = userRepository.findById(userId);
                if (user == null) {
                    logger.debug("User not found with id: {}", userId);
                    throw new ResourceNotFoundException("User not found");
                }
            logger.debug("Fetching perfume with id: {}", perfumeId);
        Perfume perfume = perfumeRepository.findById(perfumeId);
                if (perfume == null) {
                    logger.debug("Perfume not found with id: {}", perfumeId);
                    throw new ResourceNotFoundException("Perfume not found");
                }

        if (user.removeFavorite(perfume)) {
            userRepository.save(user);
            logger.debug("Perfume with id: {} removed from favorites", perfumeId);
            return perfume.getName() + " removed from favorites";
        } else {
            logger.debug("Perfume with id: {} not found in favorites for user with id: {}", perfumeId, user);
            throw new ResourceNotFoundException("There is no perfume with id: " + perfumeId + " in user's favorites with id: " + userId);}
    }
    @Override
    public String setSignature(int userId, int perfumeId){
        logger.debug("Fetching user with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.debug("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }	
        logger.debug("Fetching perfume with id: {}", perfumeId);
        Perfume perfume = perfumeRepository.findById(perfumeId);
        if (perfume == null) {
            logger.debug("Perfume not found with id: {}", perfumeId);
            throw new ResourceNotFoundException("Perfume not found");
        }
        user.setSignature(perfume);
		userRepository.save(user);
        logger.debug("Perfume with id: {} set as signature for user with id: {}", perfumeId, userId);
        return perfume.getName() + " set as signature for user with id : " + userId;
    }

    @Override
    public ReviewDto createReview(int userId, int perfumeId, ReviewDto review){
        if (reviewRepository.findByUserId(userId) != null) {
            logger.debug("User with id: {} already has a review for perfume with id: {}", userId, perfumeId);
            throw new DuplicateReviewException("User already has a review for this perfume");
        }
        logger.debug("Fetching user with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.debug("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }
        logger.debug("Fetching perfume with id: {}", perfumeId);
        Perfume perfume = perfumeRepository.findById(perfumeId);
        if (perfume == null) {
            logger.debug("Perfume not found with id: {}", perfumeId);
            throw new ResourceNotFoundException("Perfume not found");
        }
        Review reviewEntity = new Review(perfume, user, review.getTitle(), review.getDescription(), review.getVote(), review.getSeasonality(), review.isGender(), review.getSillage(), review.getLongevity());
        reviewRepository.save(reviewEntity);
        logger.debug("Review created for perfume with id: {} by user with id: {}", perfumeId, userId);
        review.setId(reviewEntity.getId());
        return review;
    }

    @Override
    public String deleteReview(int reviewId, int userId) {
        logger.debug("Fetching review with id: {}", reviewId);
        Review review = reviewRepository.findById(reviewId);
        if (review != null) {
            if (review.getUser().getId() != userId) {
                logger.debug("User with id: {} does not own the review with id: {}", userId, reviewId);
                throw new ForbiddenActionException("This review does not belong to the user with id: " + userId);
            } else {
            logger.debug("Deleting review with id: {}", reviewId);
            reviewRepository.delete(review);
            return "Review with id: " + reviewId + " deleted";}
        } else {
            logger.debug("Review not found with id: {} for user with id: {}", reviewId, userId);
            throw new ResourceNotFoundException("There is no review with id: " + reviewId + " for user with id: " + userId);}
    }


}
