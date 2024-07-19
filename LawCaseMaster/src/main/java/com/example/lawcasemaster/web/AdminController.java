package com.example.lawcasemaster.web;

import com.example.lawcasemaster.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAllRegisteredUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "admin";
    }

}
