package com.carbontrack.carbontrack;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "carbon_goals")
public class CarbonGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName;

    private Double targetEmissions;

    private LocalDate startDate;

    private LocalDate endDate;

    public CarbonGoal() {
    }

    public Long getId() {
        return id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Double getTargetEmissions() {
        return targetEmissions;
    }

    public void setTargetEmissions(Double targetEmissions) {
        this.targetEmissions = targetEmissions;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

