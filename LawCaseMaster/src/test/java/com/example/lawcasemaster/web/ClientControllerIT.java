package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.entity.Client;
import com.example.lawcasemaster.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerIT {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void clearDB() {
        clientRepository.deleteAll();
    }


    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testAddClient() throws Exception {

        mockMvc.perform(post("/add-client")
                        .param("firstName", "Pesho")
                        .param("lastName", "Peshov")
                        .param("email", "pesho@abv.bg")
                        .param("phoneNumber", "0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client"));


        Optional<Client> clientOptional = clientRepository.findByEmail("pesho@abv.bg");
        Assertions.assertTrue(clientOptional.isPresent());


    }


    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testFailAddClient() throws Exception {

        mockMvc.perform(post("/add-client")
                        .param("firstName", "")
                        .param("lastName", "Peshov")
                        .param("email", "pesho@abv.bg")
                        .param("phoneNumber", "0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-client"));


        Optional<Client> clientOptional = clientRepository.findByEmail("pesho@abv.bg");
        Assertions.assertTrue(clientOptional.isEmpty());

    }


    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testAddDuplicatedClient() throws Exception {

        Client client = new Client();
        client.setFirstName("Pesho");
        client.setLastName("Peshov");
        client.setEmail("pesho@abv.bg");
        client.setPhoneNumber("0888888888");

        clientRepository.save(client);


        mockMvc.perform(post("/add-client")
                        .param("firstName", "Pesho")
                        .param("lastName", "Peshov")
                        .param("email", "pesho@abv.bg")
                        .param("phoneNumber", "0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/add-client"));


    }
}