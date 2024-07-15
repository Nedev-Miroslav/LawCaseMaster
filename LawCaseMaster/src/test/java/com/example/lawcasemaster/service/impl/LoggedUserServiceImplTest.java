package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.entity.Role;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.model.enums.RoleType;
import com.example.lawcasemaster.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoggedUserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private SecurityContext mockSecurityContext;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private UserDetails mockUserDetails;

    @InjectMocks
    private LoggedUserServiceImpl mockLoggedUserService;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.setContext(mockSecurityContext);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
    }

    @Test
    public void testGetUser() {
        User user = new User();

        String username = "testUser";
        String email = "test@abv.bg";
        String phone = "08888";


        Role role = new Role();
        RoleType roleType = RoleType.LAWYER;
        role.setRoleType(roleType);

        user.setUsername(username);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.getRoles().add(role);

        when(mockUserDetails.getUsername()).thenReturn(username);
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User toTest = mockLoggedUserService.getUser();
        Assertions.assertNotNull(toTest);
        Assertions.assertEquals(username, toTest.getUsername());
        Assertions.assertEquals(email, toTest.getEmail());
        Assertions.assertEquals(phone, toTest.getPhoneNumber());
        Assertions.assertEquals(role, toTest.getRoles().get(0));


    }



}
