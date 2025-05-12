package com.essentia.essentiauser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiauser.entity.Perfume;
import com.essentia.essentiauser.entity.User;
import com.essentia.essentiauser.exception.ResourceNotFoundException;
import com.essentia.essentiauser.repository.PerfumeRepository;
import com.essentia.essentiauser.repository.UserRepository;
import com.essentia.essentiauser.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PerfumeRepository perfumeRepository;

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
}
