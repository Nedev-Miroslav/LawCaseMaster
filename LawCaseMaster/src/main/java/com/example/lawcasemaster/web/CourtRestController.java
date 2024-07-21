package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.entity.Court;
import com.example.lawcasemaster.service.CourtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtRestController {

    private final CourtService courtService;

    public CourtRestController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping
    public List<Court> getAllCourts() {
        return courtService.getAllCourts();
    }
}