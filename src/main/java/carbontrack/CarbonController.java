package com.carbontrack.carbontrack;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carbon")
public class CarbonController {

    private final CarbonRecordRepository repository;

    public CarbonController(CarbonRecordRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public CarbonRecord saveRecord(@RequestBody CarbonRecord record) {
        if (record.getLogDate() == null) {
            record.setLogDate(LocalDate.now());
        }
        return repository.save(record);
    }

    @GetMapping
    public List<CarbonRecord> getRecords() {
        return repository.findAll();
    }

    // Daily, weekly and monthly analytics
    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics(
            @RequestParam(defaultValue = "daily") String period) {

        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate = today;

        switch (period.toLowerCase()) {
            case "weekly":
                startDate = today.minusDays(6);
                break;

            case "monthly":
                startDate = today.withDayOfMonth(1);
                break;

            case "daily":
            default:
                startDate = today;
                break;
        }

        Double total = repository.getTotalEmissionsBetween(
                startDate, endDate);

        return Map.of(
                "period", period,
                "startDate", startDate,
                "endDate", endDate,
                "totalEmissions", total
        );
    }
}
