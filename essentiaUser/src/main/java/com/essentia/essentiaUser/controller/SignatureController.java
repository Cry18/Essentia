package com.essentia.essentiauser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiauser.service.impl.UserServiceImpl;


@RestController
@RequestMapping("/api/user/")
public class SignatureController {
    
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PutMapping("signature")
    public String setSignaure(@RequestParam(value = "userId") int userId, 
                            @RequestParam(value = "perfumeId") int perfumeId) {
        return userServiceImpl.setSignature(userId, perfumeId);
    }
}
