package org.example.controller;

import org.example.service.TrainerSummaryService;
import org.example.dto.RequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TrainerSummaryController {
    private final TrainerSummaryService trainerSummaryService;

    public TrainerSummaryController(TrainerSummaryService trainerSummaryService) {
        this.trainerSummaryService = trainerSummaryService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam String username) throws Exception {
        return ResponseEntity.ok(trainerSummaryService.get(username));
    }

}
