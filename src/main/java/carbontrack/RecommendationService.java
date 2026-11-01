package com.carbontrack.carbontrack;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    public List<String> getRecommendations(
            double travelEmissions,
            double electricityEmissions) {

        List<String> recommendations = new ArrayList<>();

        if (travelEmissions > electricityEmissions) {
            recommendations.add(
                "Travel emissions are high. Try using public transport, cycling, or walking."
            );
        }

        if (electricityEmissions > travelEmissions) {
            recommendations.add(
                "Electricity emissions are high. Switch off unused lights and appliances."
            );
        }

        if (travelEmissions == electricityEmissions) {
            recommendations.add(
                "Travel and electricity emissions are equal. Try reducing both."
            );
        }

        recommendations.add(
            "Set a weekly carbon reduction goal and track your progress."
        );

        return recommendations;
    }
}
