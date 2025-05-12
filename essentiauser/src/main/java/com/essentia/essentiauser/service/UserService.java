package com.essentia.essentiauser.service;

import com.essentia.essentiauser.dto.ReviewDto;

public interface UserService {
    public String addPerfumeToFavorites(int userId, int perfumeId);
    public String removePerfumeFromFavorites(int userId, int perfumeId);
    public String setSignature(int userId, int perfumeId);
    public ReviewDto createReview(int userId, int perfumeId, ReviewDto review);
    public String deleteReview(int reviewId, int userId);
}
