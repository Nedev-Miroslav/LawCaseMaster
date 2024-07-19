package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.AddCourtSessionDTO;
import com.example.lawcasemaster.service.CourtSessionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CourtSessionController {

    private final CourtSessionService courtSessionService;


    public CourtSessionController(CourtSessionService courtSessionService) {
        this.courtSessionService = courtSessionService;
    }

    @ModelAttribute("courtSessionData")
    public AddCourtSessionDTO addCourtSessionDTO() {
        return new AddCourtSessionDTO();
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("courtSessionError");
    }


    @GetMapping("/add-court-session")
    public String addCourtSession() {
        return "add-court-session";
    }


    @PostMapping("/add-court-session")
    public String doAddCourtSession(
            @Valid AddCourtSessionDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("courtSessionData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courtSessionData", bindingResult);

            return "redirect:/add-court-session";
        }

        boolean success = courtSessionService.create(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("courtSessionError", true);

            return "redirect:/add-court-session";
        }

        return "redirect:/court-sessions";

    }


    @GetMapping("/court-sessions")
    public String getAllCourtSession(Model model) {

        model.addAttribute("allMyCourtSessions", courtSessionService.getAllMyCourtSessions());

        return "court-sessions";
    }

    @DeleteMapping("/court-sessions/{id}")
    public String deleteCourtSession(@PathVariable("id") Long id) {

        courtSessionService.deleteCourtSession(id);

        return "redirect:/court-sessions";
    }


//    @DeleteMapping("/documents/{id}")
//
//    public String deleteDocument(@PathVariable("id") Long id) {
//
//        documentService.deleteDocument(id);
//
//        return "redirect:/documents";
//    }
}
