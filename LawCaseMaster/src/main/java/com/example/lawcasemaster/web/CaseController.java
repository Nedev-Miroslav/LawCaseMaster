package com.example.lawcasemaster.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CaseController {


    @GetMapping("/add-case")
    public String addCase() {
        return "add-case";
    }
}