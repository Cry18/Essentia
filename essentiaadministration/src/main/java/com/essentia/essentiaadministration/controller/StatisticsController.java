package com.essentia.essentiaadministration.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaadministration.dto.PerfumeDto;
import com.essentia.essentiaadministration.service.PerfumeService;



@RestController
@RequestMapping("/api/admin/")
public class StatisticsController {

    private static final Logger logger = LogManager.getLogger(StatisticsController.class);
    
    @Autowired
    private PerfumeService perfumeService;


    @GetMapping("statistics")
    public Map<String, Object> getStatistics() {
        logger.debug("GET /statistics");
        Map<String, Object> response = new HashMap<>();

        PerfumeDto mostDesiredPerfume = perfumeService.findMostDesiredPerfume();
        response.put("Most desired perfume:", mostDesiredPerfume);
        
        PerfumeDto mostAppreciatedPerfume = perfumeService.findMostAppreciatedPerfume();
        response.put("Most appriciated perfume:", mostAppreciatedPerfume);
        return response;
    }
}
