package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.UserLoginDTO;
import com.example.lawcasemaster.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class LoginController {

    private final UserService userService;


    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @ModelAttribute("loginData")
    public UserLoginDTO loginDTODTO() {
        return new UserLoginDTO();
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/login-error")
    public ModelAndView loginError() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("showError", true);
        modelAndView.addObject("loginData", new UserLoginDTO());


        return modelAndView;
    }
}

