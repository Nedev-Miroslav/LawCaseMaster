package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.UserRegistrationDTO;
import com.example.lawcasemaster.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegistrationDTO registerDTO() {
        return new UserRegistrationDTO();

    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("hasRegisterError");
    }


    @GetMapping("/register")
    public String register() {
        return "register";

    }

    @PostMapping("/register")
    public String doRegister(
            @Valid UserRegistrationDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors() || !data.getPassword().equals(data.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/users/register";

        }

        boolean success = userService.registerUser(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("hasRegisterError", true);
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/users/register";
        }

        return "redirect:/users/login";
    }

//    @PostMapping("/register")
//    public String doRegister(
//            @Valid UserRegistrationDTO data,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes
//
//    ) {
//
//        if(bindingResult.hasErrors() || !data.getPassword().equals(data.getConfirmPassword())) {
//            redirectAttributes.addFlashAttribute("registerData", data);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
//
//            return "redirect:/register";
//
//        }
//
//        boolean success = userService.register(data);
//
//        if(!success) {
//            return "redirect:register";
//        }
//
//        return "redirect:/login";
//    }


}
