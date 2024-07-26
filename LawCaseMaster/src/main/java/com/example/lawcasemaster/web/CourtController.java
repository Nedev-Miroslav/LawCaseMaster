package com.example.lawcasemaster.web;

import com.example.lawcasemaster.service.CourtService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @GetMapping("/courts")
    public String courts() {
        return "courts";
    }
}
