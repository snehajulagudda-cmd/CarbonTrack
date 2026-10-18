package com.carbontrack.carbontrack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carbon")
public class CarbonController {

    private final CarbonRecordRepository repository;

    public CarbonController(CarbonRecordRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public CarbonRecord saveRecord(@RequestBody CarbonRecord record) {
        return repository.save(record);
    }

    @GetMapping
    public List<CarbonRecord> getRecords() {
        return repository.findAll();
    }
}