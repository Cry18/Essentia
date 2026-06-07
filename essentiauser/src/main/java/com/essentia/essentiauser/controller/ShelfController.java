package com.essentia.essentiauser.controller;

import com.essentia.essentiauser.dto.ShelfDto;
import com.essentia.essentiauser.service.impl.ShelfServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/user/shelf/")
public class ShelfController {

    private static final Logger logger = LogManager.getLogger(ShelfController.class);

    @Autowired
    private ShelfServiceImpl shelfService;

    @GetMapping("detail/")
    public ShelfDto shelfDetail(@RequestParam(value = "shelfId") int shelfId) {
        int userId = getCurrentUserId();
        logger.debug("GET /shelf/detail/ - shelfId: {} userId: {}", shelfId, userId);
        return shelfService.findShelfById(shelfId, userId);
    }

    @PostMapping("create/")
    public ShelfDto createShelf(@RequestParam(value = "shelfName") String shelfName) {
        int userId = getCurrentUserId();
        logger.debug("POST /shelf/create/ - userId: {} shelfName: {}", userId, shelfName);
        return shelfService.createShelf(shelfName, userId);
    }

    @DeleteMapping("delete/")
    public ShelfDto deleteShelf(@RequestParam(value = "shelfId") int shelfId) {
        int userId = getCurrentUserId();
        logger.debug("DELETE /shelf/delete/ - shelfId: {} userId: {}", shelfId, userId);
        return shelfService.deleteShelf(shelfId, userId);
    }

    @PutMapping("add/")
    public String addToShelf(@RequestParam(value = "shelfId") int shelfId,
                             @RequestParam(value = "perfumeId") int perfumeId) {
        int userId = getCurrentUserId();
        logger.debug("PUT /shelf/add/ - shelfId: {} perfumeId: {} userId: {}", shelfId, perfumeId, userId);
        return shelfService.addPerfumeToShelf(shelfId, perfumeId, userId);
    }

    @PutMapping("remove/")
    public String removeFromShelf(@RequestParam(value = "shelfId") int shelfId,
                                  @RequestParam(value = "perfumeId") int perfumeId) {
        int userId = getCurrentUserId();
        logger.debug("PUT /shelf/remove/ - shelfId: {} perfumeId: {} userId: {}", shelfId, perfumeId, userId);
        return shelfService.removeFromShelf(shelfId, perfumeId, userId);
    }

    private int getCurrentUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
