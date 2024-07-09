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


    @GetMapping("/add-document")
    public String addDocument() {
        return "add-document";
    }


    @PostMapping("/add-document")
    public String doAddDocument(
            @Valid AddDocumentDTO data,
            @RequestParam("addDocuments") MultipartFile file,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("documentData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.documentData", bindingResult);

            return "redirect:/add-document";
        }

        boolean success = documentService.createDocument(data, file);

        if(!success) {
            redirectAttributes.addFlashAttribute("documentError", true);

            return "redirect:/add-document";
        }

//TODO make add document work

        return "redirect:/client";
    }



}
