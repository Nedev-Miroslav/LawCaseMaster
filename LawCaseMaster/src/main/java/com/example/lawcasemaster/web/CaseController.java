package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.AddCaseDTO;
import com.example.lawcasemaster.model.enums.CaseType;
import com.example.lawcasemaster.service.CaseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CaseController {


private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }


    @ModelAttribute("caseData")
    public AddCaseDTO caseData() {
        return new AddCaseDTO();
    }


    @ModelAttribute
    public void addAttributeCase(Model model) {
        model.addAttribute("caseError");
    }

    @ModelAttribute
    public void addAttributeCaseMissClient(Model model) {
        model.addAttribute("missClient");
    }



    @GetMapping("/add-case")
    public ModelAndView addCase() {
        ModelAndView modelAndView = new ModelAndView("add-case");
        modelAndView.addObject("caseTypes", CaseType.values());
        return modelAndView;
    }

    @PostMapping("/add-case")
    public String doAddCase(
            @Valid AddCaseDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("caseData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.caseData", bindingResult);


            return "redirect:/add-case";
        }

        boolean success = caseService.createCase(data);

        boolean checkClient = caseService.checkIfClientExist(data);

        if(!checkClient) {
            redirectAttributes.addFlashAttribute("missClient", true);

            return "redirect:/add-case";
        }


        if (!success) {
            redirectAttributes.addFlashAttribute("caseError", true);


            return "redirect:/add-case";
        }


        return "redirect:/cases";

    }


    @GetMapping("/cases")
    public String getAllCases(Model model) {

        model.addAttribute("allMyCases", caseService.getAllMyCases());

        return "cases";
    }


    @DeleteMapping("/cases/{id}")

    public String deleteCase(@PathVariable("id") Long id) {

        caseService.deleteCase(id);

        return "redirect:/cases";
    }




}