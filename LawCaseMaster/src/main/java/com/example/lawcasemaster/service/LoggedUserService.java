package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserService {
    private static final String ROLE_PREFIX = "ROLE_";
    private final UserRepository userRepository;


    public LoggedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }



    public User getUser() {
        return userRepository.findByUsername(getUserDetails().getUsername())
                .orElse(null);
    }


    public boolean hasRole(String role) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_PREFIX + role));
    }

    public UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    public boolean isAuthenticated() {
          return !hasRole("ANONYMOUS");
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
