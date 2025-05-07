package com.essentia.essentiaUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaUser.service.PerfumeService;


@RestController
@RequestMapping("/api/user/")
public class SignatureController {
    
    @Autowired
    private PerfumeService perfumeService;

    @GetMapping("signature")
    public void setSignaure(@RequestParam(value = "userId") int userId, 
                            @RequestParam(value = "PerfumeId") int perfumeId) {
        perfumeService.setSignaure(userId, perfumeId);
    }
}
