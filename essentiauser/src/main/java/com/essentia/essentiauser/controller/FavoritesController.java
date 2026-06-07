package com.essentia.essentiauser.controller;

import com.essentia.essentiauser.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/favorites/")
public class FavoritesController {

    private static final Logger logger = LogManager.getLogger(FavoritesController.class);

    @Autowired
    private UserServiceImpl userService;

    @PutMapping("add/")
    public String addToFavorites(@RequestParam(value = "perfumeId") int perfumeId) {
        int userId = getCurrentUserId();
        logger.debug("PUT /favorites/add/ - userId: {} perfumeId: {}", userId, perfumeId);
        return userService.addPerfumeToFavorites(userId, perfumeId);
    }

    @PutMapping("remove/")
    public String removeFromFavorites(@RequestParam(value = "perfumeId") int perfumeId) {
        int userId = getCurrentUserId();
        logger.debug("PUT /favorites/remove/ - userId: {} perfumeId: {}", userId, perfumeId);
        return userService.removePerfumeFromFavorites(userId, perfumeId);
    }

    private int getCurrentUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
