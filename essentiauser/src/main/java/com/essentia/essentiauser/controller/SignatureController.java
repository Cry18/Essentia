package com.essentia.essentiauser.controller;

import com.essentia.essentiauser.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
public class SignatureController {

    private static final Logger logger = LogManager.getLogger(SignatureController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PutMapping("signature")
    public String setSignature(@RequestParam(value = "perfumeId") int perfumeId) {
        int userId = getCurrentUserId();
        logger.debug("PUT /signature - userId: {} perfumeId: {}", userId, perfumeId);
        return userServiceImpl.setSignature(userId, perfumeId);
    }

    private int getCurrentUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
