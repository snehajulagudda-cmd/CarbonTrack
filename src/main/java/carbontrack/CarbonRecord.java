package com.carbontrack.carbontrack;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CarbonRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double distance;
    private String vehicleType;
    private double electricity;
    private double totalEmissions;

    public CarbonRecord() {
    }

    public CarbonRecord(double distance, String vehicleType,
                        double electricity, double totalEmissions) {
        this.distance = distance;
        this.vehicleType = vehicleType;
        this.electricity = electricity;
        this.totalEmissions = totalEmissions;
    }

    public Long getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getTotalEmissions() {
        return totalEmissions;
    }

    public void setTotalEmissions(double totalEmissions) {
        this.totalEmissions = totalEmissions;
    }
}