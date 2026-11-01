package com.carbontrack.carbontrack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<String> getRecommendations(
            @RequestParam double travelEmissions,
            @RequestParam double electricityEmissions) {

        return recommendationService.getRecommendations(
                travelEmissions,
                electricityEmissions
        );
    }
}

