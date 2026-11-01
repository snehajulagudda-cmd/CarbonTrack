package com.carbontrack.carbontrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarbonRecordRepository
        extends JpaRepository<CarbonRecord, Long> {

    // Get records within a date range
    List<CarbonRecord> findByLogDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    // Calculate total emissions within a date range
    @Query("SELECT COALESCE(SUM(c.totalEmissions), 0) " +
           "FROM CarbonRecord c " +
           "WHERE c.logDate BETWEEN :startDate AND :endDate")
    Double getTotalEmissionsBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
