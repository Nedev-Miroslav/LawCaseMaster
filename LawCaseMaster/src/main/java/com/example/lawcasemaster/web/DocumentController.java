package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import com.example.lawcasemaster.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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
    public String getAllDocument(Model model) {

        model.addAttribute("allMyDocuments", documentService.getAllMyDocuments());


        return "documents";
    }

    @GetMapping("/download-document")
    public ResponseEntity<Resource> downloadDocument(@RequestParam("filePath") String filePath) throws IOException {
        Path path = Paths.get(filePath).normalize();
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(path);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/documents/{id}")

    public String deleteDocument(@PathVariable("id") Long id) {

        documentService.deleteDocument(id);

        return "redirect:/documents";
    }




}



