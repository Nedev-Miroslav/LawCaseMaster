package com.example.lawcasemaster.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
@CrossOrigin("*")
@Controller
public class MainController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }



    @GetMapping("/about")
    public String getAbout() {

        return "about";
    }
}
