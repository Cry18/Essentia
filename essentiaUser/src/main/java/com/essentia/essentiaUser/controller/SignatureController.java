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
@RequestMapping("/api/user/")
public class SignatureController {
    
    private static final Logger logger = LogManager.getLogger(SignatureController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PutMapping("signature")
    public String setSignaure(@RequestParam(value = "userId") int userId, 
                            @RequestParam(value = "perfumeId") int perfumeId) {
        logger.debug("PUT /signature - userId: {} perfumeId: {}", userId, perfumeId);
        return userServiceImpl.setSignature(userId, perfumeId);
    }

}
