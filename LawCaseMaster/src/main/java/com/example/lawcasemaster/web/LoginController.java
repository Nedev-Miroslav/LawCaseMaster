package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.UserLoginDTO;
import com.example.lawcasemaster.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        System.out.println("TEST");
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("showError", true);
        modelAndView.addObject("loginData", new UserLoginDTO());


        return modelAndView;
    }
}

