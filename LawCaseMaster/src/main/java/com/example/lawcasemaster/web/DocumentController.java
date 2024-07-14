package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import com.example.lawcasemaster.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;

    }


    @ModelAttribute("documentData")
    public AddDocumentDTO documentData() {
        return new AddDocumentDTO();
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("documentError");
    }

    @ModelAttribute
    public void addAttributeCaseMissCase(Model model) {
        model.addAttribute("missCase");
    }

    @ModelAttribute
    public void addAttributeExistDocument(Model model) {
        model.addAttribute("existDocument");
    }


    @GetMapping("/add-document")
    public String addDocument() {
        return "add-document";
    }


    @PostMapping("/add-document")
    public String doAddDocument(
            @Valid AddDocumentDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @RequestParam("addDocument") MultipartFile file
    ) throws IOException {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("documentData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.documentData", bindingResult);

            return "redirect:/add-document";
        }

        boolean checkValidCaseNumber = documentService.checkValidCaseNumberInput(data);

        if (!checkValidCaseNumber) {
            redirectAttributes.addFlashAttribute("missCase", true);

            return "redirect:/add-document";
        }

        boolean existDocumentByNumber = documentService.existDock(data);


        if (!existDocumentByNumber) {
            redirectAttributes.addFlashAttribute("existDocument", true);

            return "redirect:/add-document";
        }


        boolean success = documentService.createDocument(data, file);


        if (!success) {
            redirectAttributes.addFlashAttribute("documentError", true);

            return "redirect:/add-document";
        }


        return "redirect:/documents";
    }

    @GetMapping("/documents")
    public String getAllDocument() {


        return "documents";
    }


}
