package com.example.lawcasemaster.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourtController {
    @GetMapping("/courts")
    public String courts() {
        return "courts";
    }
}
