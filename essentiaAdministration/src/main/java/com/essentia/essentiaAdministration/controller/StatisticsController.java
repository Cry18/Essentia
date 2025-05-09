package com.essentia.essentiaAdministration.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaAdministration.dto.PerfumeDto;
import com.essentia.essentiaAdministration.service.PerfumeService;



@RestController
@RequestMapping("/api/admin/")
public class StatisticsController {
    
    @Autowired
    private PerfumeService perfumeService;


    @GetMapping("statistics")
    public Map<String, Object> getStatistics() {
        Map<String, Object> response = new HashMap<>();

        PerfumeDto mostDesiredPerfume = perfumeService.findMostDesiredPerfume();
        response.put("Profumo più desiderato", mostDesiredPerfume);
        
        PerfumeDto mostAppreciatedPerfume = perfumeService.findMostAppreciatedPerfume();
        response.put("Profumo più apprezzato", mostAppreciatedPerfume);
        return response;
    }
}
