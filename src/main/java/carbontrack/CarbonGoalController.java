package com.carbontrack.carbontrack;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "*")
public class CarbonGoalController {

    private final CarbonGoalRepository goalRepository;
    private final CarbonRecordRepository carbonRecordRepository;

    public CarbonGoalController(
            CarbonGoalRepository goalRepository,
            CarbonRecordRepository carbonRecordRepository) {
        this.goalRepository = goalRepository;
        this.carbonRecordRepository = carbonRecordRepository;
    }

    @PostMapping
    public CarbonGoal createGoal(@RequestBody CarbonGoal goal) {
        return goalRepository.save(goal);
    }

    @GetMapping
    public List<CarbonGoal> getAllGoals() {
        return goalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarbonGoal> getGoalById(
            @PathVariable Long id) {
        return goalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarbonGoal> updateGoal(
            @PathVariable Long id,
            @RequestBody CarbonGoal updatedGoal) {

        return goalRepository.findById(id)
                .map(goal -> {
                    goal.setGoalName(updatedGoal.getGoalName());
                    goal.setTargetEmissions(
                            updatedGoal.getTargetEmissions());
                    goal.setStartDate(updatedGoal.getStartDate());
                    goal.setEndDate(updatedGoal.getEndDate());

                    CarbonGoal savedGoal =
                            goalRepository.save(goal);

                    return ResponseEntity.ok(savedGoal);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(
            @PathVariable Long id) {

        if (!goalRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        goalRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<Map<String, Object>> getGoalProgress(
            @PathVariable Long id) {

        return goalRepository.findById(id)
                .map(goal -> {
                    LocalDate startDate = goal.getStartDate();
                    LocalDate endDate = goal.getEndDate();

                    double actualEmissions =
                            carbonRecordRepository.findAll()
                                    .stream()
                                    .filter(record ->
                                            record.getLogDate() != null)
                                    .filter(record ->
                                            !record.getLogDate()
                                                    .isBefore(startDate))
                                    .filter(record ->
                                            !record.getLogDate()
                                                    .isAfter(endDate))
                                    .mapToDouble(record ->
                                            record.getTotalEmissions())
                                    .sum();

                    double targetEmissions =
                            goal.getTargetEmissions() == null
                                    ? 0.0
                                    : goal.getTargetEmissions();

                    double remainingEmissions =
                            Math.max(0.0,
                                    targetEmissions - actualEmissions);

                    double progressPercentage = targetEmissions > 0
                            ? (actualEmissions / targetEmissions) * 100.0
                            : 0.0;

                    boolean goalAchieved =
                            actualEmissions <= targetEmissions;

                    Map<String, Object> progress = Map.of(
                            "goalId", goal.getId(),
                            "goalName", goal.getGoalName(),
                            "targetEmissions", targetEmissions,
                            "actualEmissions", actualEmissions,
                            "remainingEmissions", remainingEmissions,
                            "progressPercentage",
                            Math.round(progressPercentage * 100.0) / 100.0,
                            "goalAchieved", goalAchieved,
                            "startDate", startDate,
                            "endDate", endDate
                    );

                    return ResponseEntity.ok(progress);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
