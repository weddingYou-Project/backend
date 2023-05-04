package com.mysite.weddingyou_backend.planner.register;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlannerController {
    private final PlannerService plannerService;

    @Autowired
    public PlannerController(PlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @PostMapping("/planner/register")
    public ResponseEntity<Planner> createPlanner(@Valid @RequestBody PlannerDTO plannerDTO) {
        Planner createdPlanner = plannerService.createPlanner(plannerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlanner);
    }
}
