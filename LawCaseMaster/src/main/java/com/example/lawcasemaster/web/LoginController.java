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

    @ModelAttribute
    public void addAttributeLoginError(Model model) {
        model.addAttribute("hasLoginError");
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
//
//    @PostMapping("/users/login-error")
//    public String doLogin(
//            @Valid UserLoginDTO data,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes
//    ) {
//
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("loginData", data);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
//
//            return "redirect:/users/login?error";
//
//
//        }
//
//        boolean success = userService.login(data);
//
//        if (!success) {
//            redirectAttributes.addFlashAttribute("hasLoginError", true);
//            redirectAttributes.addFlashAttribute("hasLoginError", "Incorrect username or password!");
//
//            return "redirect:/users/login/error";
//        }
//
//
//        return "redirect:/users/index";
//    }



    @PostMapping("/login-error")
    public String handleFailedLogin(

            @Valid UserLoginDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);

            System.out.println("test");
            return "redirect:/users/login/error";

        }

        boolean success = userService.login(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("hasLoginError", true);
            redirectAttributes.addFlashAttribute("hasLoginError", "Incorrect username or password!");

            return "redirect:/users/login/error";
        }

        System.out.println("test");
        return "redirect:/users/index";
    }

}
