package com.essentia.essentiauser.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import com.essentia.essentiauser.dto.PerfumeSummaryDto;
import com.essentia.essentiauser.dto.ReviewDto;
import com.essentia.essentiauser.dto.ShelfDto;
import com.essentia.essentiauser.dto.UserProfileDto;
import com.essentia.essentiauser.entity.Perfume;
import com.essentia.essentiauser.entity.Review;
import com.essentia.essentiauser.entity.Shelf;
import com.essentia.essentiauser.entity.User;
import com.essentia.essentiauser.exception.DuplicateReviewException;
import com.essentia.essentiauser.exception.ForbiddenActionException;
import com.essentia.essentiauser.exception.ResourceNotFoundException;
import com.essentia.essentiauser.repository.PerfumeRepository;
import com.essentia.essentiauser.repository.ReviewRepository;
import com.essentia.essentiauser.repository.ShelfRepository;
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
    @Autowired
    private ShelfRepository shelfRepository;

    @Override
    public String addPerfumeToFavorites(int userId, int perfumeId) {
        logger.debug("Fetching user with id: {}", userId);
            User user = userRepository.findById(userId);
                if (user == null) {
                    logger.warn("User not found with id: {}", userId);
                    throw new ResourceNotFoundException("User not found");
                }
            logger.debug("Fetching perfume with id: {}", perfumeId);
            Perfume perfume = perfumeRepository.findById(perfumeId);
                if (perfume == null) {
                    logger.warn("Perfume not found with id: {}", perfumeId);
                    throw new ResourceNotFoundException("Perfume not found");
                }
            if (user.getFavorites().contains(perfume)) {
                logger.warn("Perfume with id: {} already in favorites", perfumeId);
                throw new ResourceNotFoundException("Perfume already in favorites");
            }
            user.addFavorite(perfume);
            userRepository.save(user);
            logger.info("Perfume with id: {} added to favorites", perfumeId);
            return perfume.getName() + " added to favorites";
        }

    @Override
    public String removePerfumeFromFavorites(int userId, int perfumeId) {
        logger.debug("Fetching user with id: {}", userId);
        User user = userRepository.findById(userId);
                if (user == null) {
                    logger.warn("User not found with id: {}", userId);
                    throw new ResourceNotFoundException("User not found");
                }
            logger.debug("Fetching perfume with id: {}", perfumeId);
        Perfume perfume = perfumeRepository.findById(perfumeId);
                if (perfume == null) {
                    logger.warn("Perfume not found with id: {}", perfumeId);
                    throw new ResourceNotFoundException("Perfume not found");
                }

        if (user.removeFavorite(perfume)) {
            userRepository.save(user);
            logger.info("Perfume with id: {} removed from favorites", perfumeId);
            return perfume.getName() + " removed from favorites";
        } else {
            logger.warn("Perfume with id: {} not found in favorites for user with id: {}", perfumeId, user);
            throw new ResourceNotFoundException("There is no perfume with id: " + perfumeId + " in user's favorites with id: " + userId);}
    }

    @Override
    public String setSignature(int userId, int perfumeId){
        logger.debug("Fetching user with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.warn("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }	
        logger.debug("Fetching perfume with id: {}", perfumeId);
        Perfume perfume = perfumeRepository.findById(perfumeId);
        if (perfume == null) {
            logger.warn("Perfume not found with id: {}", perfumeId);
            throw new ResourceNotFoundException("Perfume not found");
        }
        user.setSignature(perfume);
		userRepository.save(user);
        logger.info("Perfume with id: {} set as signature for user with id: {}", perfumeId, userId);
        return perfume.getName() + " set as signature for user with id : " + userId;
    }

    @Override
    public ReviewDto createReview(int userId, int perfumeId, ReviewDto review){
        if (reviewRepository.findByUserIdAndPerfumeId(userId, perfumeId) != null) {
            logger.warn("User with id: {} already has a review for perfume with id: {}", userId, perfumeId);
            throw new DuplicateReviewException("User already has a review for this perfume");
        }
        logger.debug("Fetching user with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.warn("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }
        logger.debug("Fetching perfume with id: {}", perfumeId);
        Perfume perfume = perfumeRepository.findById(perfumeId);
        if (perfume == null) {
            logger.warn("Perfume not found with id: {}", perfumeId);
            throw new ResourceNotFoundException("Perfume not found");
        }
        Review reviewEntity = new Review(perfume, user, review.getTitle(), review.getDescription(), review.getVote(), review.getSeasonality(), review.isGender(), review.getSillage(), review.getLongevity());
        reviewRepository.save(reviewEntity);
        logger.info("Review created for perfume with id: {} by user with id: {}", perfumeId, userId);
        review.setId(reviewEntity.getId());
        return review;
    }

    @Override
    @Transactional
    public UserProfileDto getUserProfile(int userId) {
        logger.debug("Fetching profile for user with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.warn("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }

        UserProfileDto profile = new UserProfileDto();
        profile.setUsername(user.getUsername());
        profile.setName(user.getName());
        profile.setSurname(user.getSurname());
        profile.setImageUrl(user.getImageUrl());

        // Signature
        if (user.getSignature() != null) {
            Perfume sig = user.getSignature();
            String brandName = sig.getBrand() != null ? sig.getBrand().getName() : null;
            profile.setSignature(new PerfumeSummaryDto(sig.getId(), sig.getName(), brandName, sig.getImageUrl()));
        }

        // Favorites
        List<PerfumeSummaryDto> favorites = user.getFavorites().stream()
                .map(p -> {
                    String brandName = p.getBrand() != null ? p.getBrand().getName() : null;
                    return new PerfumeSummaryDto(p.getId(), p.getName(), brandName, p.getImageUrl());
                })
                .collect(Collectors.toList());
        profile.setFavorites(favorites);

        // Shelves (con LEFT JOIN FETCH perfumes per evitare LazyInitializationException)
        List<Shelf> shelves = shelfRepository.findByUserId(userId);
        List<ShelfDto> shelvesDto = shelves.stream()
                .map(s -> {
                    ShelfDto dto = new ShelfDto(s.getName());
                    dto.setId(s.getId());
                    List<PerfumeSummaryDto> perfumeItems = s.getPerfumes().stream()
                            .map(p -> {
                                String brandName = p.getBrand() != null ? p.getBrand().getName() : null;
                                return new PerfumeSummaryDto(p.getId(), p.getName(), brandName, p.getImageUrl());
                            })
                            .collect(Collectors.toList());
                    dto.setPerfumes(perfumeItems);
                    return dto;
                })
                .collect(Collectors.toList());
        profile.setShelves(shelvesDto);

        logger.info("Profile loaded for user with id: {}", userId);
        return profile;
    }

    @Override
    public String updateReview(int reviewId, int userId, ReviewDto review) {
        logger.debug("Fetching review with id: {}", reviewId);
        Review existing = reviewRepository.findById(reviewId);
        if (existing == null) {
            logger.warn("Review not found with id: {}", reviewId);
            throw new ResourceNotFoundException("Review not found with id: " + reviewId);
        }
        if (existing.getUser().getId() != userId) {
            logger.warn("User with id: {} does not own review with id: {}", userId, reviewId);
            throw new ForbiddenActionException("This review does not belong to the user with id: " + userId);
        }
        existing.setTitle(review.getTitle());
        existing.setDescription(review.getDescription());
        existing.setVote(review.getVote());
        existing.setSeasonality(review.getSeasonality());
        existing.setGender(review.isGender());
        existing.setSillage(review.getSillage());
        existing.setLongevity(review.getLongevity());
        reviewRepository.save(existing);
        logger.info("Review with id: {} updated by user with id: {}", reviewId, userId);
        return "Review with id: " + reviewId + " updated";
    }

    @Override
    public void updateProfileImage(int userId, String imageUrl) {
        logger.debug("Updating profile image for user with id: {}", userId);
        User user = userRepository.findById(userId);
        if (user == null) {
            logger.warn("User not found with id: {}", userId);
            throw new ResourceNotFoundException("User not found");
        }
        user.setImageUrl(imageUrl);
        userRepository.save(user);
        logger.info("Profile image updated for user with id: {}", userId);
    }

    @Override
    public String deleteReview(int reviewId, int userId) {
        logger.debug("Fetching review with id: {}", reviewId);
        Review review = reviewRepository.findById(reviewId);
        if (review != null) {
            if (review.getUser().getId() != userId) {
                logger.warn("User with id: {} does not own the review with id: {}", userId, reviewId);
                throw new ForbiddenActionException("This review does not belong to the user with id: " + userId);
            } else {
            reviewRepository.delete(review);
            logger.info("Review with id: {}, deleted", reviewId);
            return "Review with id: " + reviewId + " deleted";}
        } else {
            logger.warn("Review not found with id: {} for user with id: {}", reviewId, userId);
            throw new ResourceNotFoundException("There is no review with id: " + reviewId + " for user with id: " + userId);}
    }


}
