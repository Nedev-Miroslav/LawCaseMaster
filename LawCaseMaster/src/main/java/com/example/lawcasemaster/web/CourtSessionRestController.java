package com.example.lawcasemaster.web;

import com.example.lawcasemaster.service.CourtSessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourtSessionRestController {

    private final CourtSessionService courtSessionService;


    public CourtSessionRestController(CourtSessionService courtSessionService) {
        this.courtSessionService = courtSessionService;
    }












}
