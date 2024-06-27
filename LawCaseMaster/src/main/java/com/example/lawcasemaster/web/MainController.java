package com.example.lawcasemaster.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Начална страница");
        return "index";
    }

//    @GetMapping("/register")
//    public String users(Model model) {
//        model.addAttribute("title", "Потребители");
//        return "register";
//    }

    @GetMapping("/login")
    public String cases(Model model) {
        model.addAttribute("title", "Случаи");
        return "login";
    }

    @GetMapping("/about")
    public String tasks(Model model) {
        model.addAttribute("title", "Задачи");
        return "about";
    }

    @GetMapping("/court-sessions")
    public String courtSessions(Model model) {
        model.addAttribute("title", "Съдебни сесии");
        return "court-sessions";
    }

    @GetMapping("/documents")
    public String documents(Model model) {
        model.addAttribute("title", "Документи");
        return "documents";
    }

    @GetMapping("/add-case")
    public String createCase(Model model) {
        model.addAttribute("title", "Създаване на нов случай");
        return "add-case";
    }

    @GetMapping("/create-user")
    public String createUser(Model model) {
        model.addAttribute("title", "Създаване на нов потребител");
        return "create-user";
    }

    @GetMapping("/create-task")
    public String createTask(Model model) {
        model.addAttribute("title", "Създаване на нова задача");
        return "create-task";
    }

    @GetMapping("/create-court-session")
    public String createCourtSession(Model model) {
        model.addAttribute("title", "Създаване на нова съдебна сесия");
        return "create-court-session";
    }

    @GetMapping("/create-document")
    public String createDocument(Model model) {
        model.addAttribute("title", "Създаване на нов документ");
        return "create-document";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Контакти");
        return "contact";
    }
}
