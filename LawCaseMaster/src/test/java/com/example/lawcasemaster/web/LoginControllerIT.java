package com.example.lawcasemaster.web;

import com.example.lawcasemaster.LawCaseMasterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = LawCaseMasterApplication.class) // Зареждам целия контекст на Spring Boot с основния клас на приложението
@AutoConfigureMockMvc
public class LoginControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginData"));
    }

    @Test
    public void testLoginErrorPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/login-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("showError"))
                .andExpect(model().attribute("showError", true))
                .andExpect(model().attributeExists("loginData"))
                .andExpect(content().string(containsString("login")))
                .andReturn();
    }
}
