package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.UserRegistrationDTO;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.RoleRepository;
import com.example.lawcasemaster.repository.UserRepository;
import com.example.lawcasemaster.service.LoggedUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl toTest;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private LoggedUserService mockLoggedUserService;


    @BeforeEach
    public void setUp() {
        toTest = new UserServiceImpl(
                mockUserRepository,
                mockRoleRepository,
                new ModelMapper(),
                mockPasswordEncoder,
                mockLoggedUserService
        );
    }


    @Test
    public void testUserRegistration() {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername("test");
        userRegistrationDTO.setEmail("test@abv.bg");
        userRegistrationDTO.setPhoneNumber("0888888");
        userRegistrationDTO.setPassword("testPassword");
        userRegistrationDTO.setConfirmPassword("testPassword");


        when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
                .thenReturn(userRegistrationDTO.getPassword() + "simulateEncoding");


        toTest.registerUser(userRegistrationDTO);


        verify(mockUserRepository).save(userCaptor.capture());

        User actualSavedEntity = userCaptor.getValue();


        Assertions.assertEquals(userRegistrationDTO.getUsername(), actualSavedEntity.getUsername());
        Assertions.assertEquals(userRegistrationDTO.getEmail(), actualSavedEntity.getEmail());
        Assertions.assertEquals(userRegistrationDTO.getPhoneNumber(), actualSavedEntity.getPhoneNumber());

        Assertions.assertEquals(userRegistrationDTO.getPassword() + "simulateEncoding", actualSavedEntity.getPassword());

    }
}
