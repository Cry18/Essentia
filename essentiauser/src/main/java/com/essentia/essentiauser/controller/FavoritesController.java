package com.essentia.essentiauser.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiauser.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/user/favorites/")
public class FavoritesController {

  private static final Logger logger = LogManager.getLogger(FavoritesController.class);
  
   @Autowired
   private UserServiceImpl userService;

    @PutMapping("add/")
    public String addToFavorites(@RequestParam(value = "userId") int userId,
                            @RequestParam(value = "perfumeId") int perfumeId) {
      logger.debug("PUT /favorites/add/ - userId: {} perfumeId: {}", userId, perfumeId);
      return userService.addPerfumeToFavorites(userId, perfumeId);
    }

    @PutMapping("remove/")
    public String removeFromFavorites(@RequestParam(value = "userId") int userId,
                            @RequestParam(value = "perfumeId") int perfumeId) {
      logger.debug("PUT /favorites/remove/ - userId: {} perfumeId: {}", userId, perfumeId);
      return userService.removePerfumeFromFavorites(userId, perfumeId);
    }

}
