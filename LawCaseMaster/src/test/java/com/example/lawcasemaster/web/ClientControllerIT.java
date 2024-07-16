package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.entity.Client;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.ClientRepository;
import com.example.lawcasemaster.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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

    @Autowired
    private UserRepository userRepository;
    @MockBean
    private User testUser;


    @BeforeEach
    public void clearDB() {
        clientRepository.deleteAll();

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("user1");
        testUser.setPhoneNumber("000000000");
        testUser.setPassword("testPassword");
        testUser.setEmail("test@abv.bg");


        userRepository.save(testUser);

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


    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testGetAllClients() throws Exception {
        Client client1 = new Client();
        client1.setFirstName("Pesho");
        client1.setLastName("Peshov");
        client1.setEmail("pesho1@abv.bg");
        client1.setPhoneNumber("0888888888");
        client1.setUser(testUser);
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setFirstName("Gosho");
        client2.setLastName("Goshov");
        client2.setEmail("gosho@abv.bg");
        client2.setPhoneNumber("0777777777");
        client2.setUser(testUser);
        clientRepository.save(client2);


        mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(view().name("client"));



    }

    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testDeleteClient() throws Exception {
        Client client = new Client();
        client.setFirstName("Pesho");
        client.setLastName("Peshov");
        client.setEmail("pesho@abv.bg");
        client.setPhoneNumber("0888888888");
        client.setUser(testUser);

        clientRepository.save(client);

        Long clientId = client.getId();

        mockMvc.perform(delete("/client/{id}", clientId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client"));

        Optional<Client> clientOptional = clientRepository.findById(clientId);
        Assertions.assertTrue(clientOptional.isEmpty());
    }

}




