package com.essentia.essentiaAdministration.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essentia.essentiaAdministration.repository.ReviewRepository;



@RestController
@RequestMapping("/api/admin/")
public class StatisticsController {
    /*/
    @Autowired
    private FavoritesRepository favoritesRepository;
    */
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        Map<String, Object> response = new HashMap<>();

        /*/
        List<String> mostDesiredPerfumes = favoritesRepository.findMostDesiredPerfumes();
        response.put("Profumi più desiderati", mostDesiredPerfumes);
        */

        // Retrieve most appreciated perfume
        String mostAppreciatedPerfume = reviewRepository.findMostAppreciatedPerfume();
        response.put("Profumo più apprezzato", mostAppreciatedPerfume);

        return response;
    }
}
