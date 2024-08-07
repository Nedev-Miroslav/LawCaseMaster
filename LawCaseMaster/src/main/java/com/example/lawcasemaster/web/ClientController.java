package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.AddClientDTO;
import com.example.lawcasemaster.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientController {


    private final ClientService clientService;


    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ModelAttribute("clientData")
    public AddClientDTO clientData() {
        return new AddClientDTO();
    }

    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("clientError");
    }


    @GetMapping("/add-client")
    public String addClient() {
        return "add-client";
    }


    @PostMapping("/add-client")
    public String doAddClient(
            @Valid AddClientDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("clientData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clientData", bindingResult);

            return "redirect:/add-client";
        }

        boolean success = clientService.create(data);

        if (!success) {
            redirectAttributes.addFlashAttribute("clientError", true);

            return "redirect:/add-client";
        }

        return "redirect:/client";

    }



    @GetMapping("/client")
    public String getAllClient(Model model){

        model.addAttribute("allMyClients", clientService.getAllMyClients());

        return "client";
    }



    @DeleteMapping("/client/{id}")
    public String deleteClient(@PathVariable("id") Long id) {

        clientService.deleteClient(id);

        return "redirect:/client";
    }


}
