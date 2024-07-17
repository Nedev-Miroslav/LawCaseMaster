package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void clearDB(){
        userRepository.deleteAll();
    }

    @Test
    public void testGetRegistration() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testRegistration() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("username", "testUsername")
                        .param("email", "test@abv.bg")
                        .param("phoneNumber", "088888")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

        Optional<User> userOptional = userRepository.findByUsername("testUsername");

        Assertions.assertTrue(userOptional.isPresent());

        User user = userOptional.get();

        Assertions.assertEquals("testUsername", user.getUsername());
        Assertions.assertEquals("test@abv.bg", user.getEmail());
        Assertions.assertEquals("088888", user.getPhoneNumber());
        Assertions.assertTrue(passwordEncoder.matches("testPassword", user.getPassword()));
    }


    @Test
    public void testFailRegistration() throws Exception {

        mockMvc.perform(post("/users/register")
                        .param("username", "testUsername")
                        .param("email", "")
                        .param("phoneNumber", "088888")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));


        Optional<User> userOptional = userRepository.findByUsername("testUsername");

        Assertions.assertTrue(userOptional.isEmpty());

    }


    @Test
    public void testRegisterDuplicatedUser() throws Exception {
        User user = new User();
            user.setUsername("testUsername");
            user.setEmail("test@abv.bg");
            user.setPhoneNumber("088888");
            user.setPassword("testPassword");

        userRepository.save(user);

        mockMvc.perform(post("/users/register")
                        .param("username", "testUsername")
                        .param("email", "test@abv.bg")
                        .param("phoneNumber", "088888")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPassword")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));


    }

    @Test
    public void testRegisterMissMatchPassword() throws Exception {
        User user = new User();
        user.setUsername("testUsername");
        user.setEmail("test@abv.bg");
        user.setPhoneNumber("088888");
        user.setPassword("testPassword");

        userRepository.save(user);

        mockMvc.perform(post("/users/register")
                        .param("username", "testUsername")
                        .param("email", "test@abv.bg")
                        .param("phoneNumber", "088888")
                        .param("password", "testPassword")
                        .param("confirmPassword", "testPasswordDiff")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));


    }





}
