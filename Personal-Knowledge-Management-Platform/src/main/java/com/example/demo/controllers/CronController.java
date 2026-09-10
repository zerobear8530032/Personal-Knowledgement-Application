package com.example.demo.controllers;

import com.example.demo.dtos.CreateSchedulerExpressionRequest;
import com.example.demo.dtos.SchedulerExpressionResponse;
import com.example.demo.dtos.UpdateSchedulerExpressionRequest;
import com.example.demo.services.SchdulerExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crons")
public class CronController {

    private final SchdulerExpressionService schdulerExpressionService;

    @Autowired
    public CronController(SchdulerExpressionService schdulerExpressionService) {
        this.schdulerExpressionService = schdulerExpressionService;
    }

    @GetMapping
    public ResponseEntity<List<SchedulerExpressionResponse>> getAllCronExpressions() {
        return ResponseEntity.ok(
                schdulerExpressionService.getAllCronsExpressions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulerExpressionResponse> getCronExpressionById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                schdulerExpressionService.getCronsExpressionById(id)
        );
    }

    @PostMapping
    public ResponseEntity<SchedulerExpressionResponse> createCronExpression(
            @RequestBody CreateSchedulerExpressionRequest request
    ) {
        SchedulerExpressionResponse response =
                schdulerExpressionService.createCronExpression(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<SchedulerExpressionResponse> updateCronExpression(
            @RequestBody UpdateSchedulerExpressionRequest request
    ) {
        return ResponseEntity.ok(
                schdulerExpressionService.updateCronExpression(request)
        );
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<String> startScheduler(
            @PathVariable Long id
    ) {
        schdulerExpressionService.startScheduler(id);

        return ResponseEntity.ok("Scheduler started successfully");
    }

}