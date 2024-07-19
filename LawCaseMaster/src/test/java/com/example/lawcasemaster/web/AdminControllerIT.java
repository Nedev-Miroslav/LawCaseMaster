package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.UserRepository;
import com.example.lawcasemaster.service.UserService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private User testUser;


    @Test
    @WithMockUser(username = "user1", roles = {"ADMIN"})
    public void testIndex() throws Exception {




        mockMvc.perform(get("/admin"))
                       .andExpect(status().isOk())
                      .andExpect(view().name("admin"));




    }



}
