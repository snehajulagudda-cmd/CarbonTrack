package com.carbontrack.carbontrack;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarbonRecordRepository
        extends JpaRepository<CarbonRecord, Long> {

}