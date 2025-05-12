package com.essentia.essentiauser.service;

public interface UserService {
    public String addPerfumeToFavorites(int userId, int perfumeId);
    public String removePerfumeFromFavorites(int userId, int perfumeId);
}
