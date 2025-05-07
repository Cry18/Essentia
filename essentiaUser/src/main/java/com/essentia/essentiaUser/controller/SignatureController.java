package com.essentia.essentiaUser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaUser.service.impl.PerfumeServiceImpl;


@RestController
@RequestMapping("/api/user/")
public class SignatureController {
    
    @Autowired
    private PerfumeServiceImpl perfumeService;

    @PutMapping("signature")
    public void setSignaure(@RequestParam(value = "userId") int userId, 
                            @RequestParam(value = "perfumeId") int perfumeId) {
        perfumeService.setSignature(userId, perfumeId);
    }
}
