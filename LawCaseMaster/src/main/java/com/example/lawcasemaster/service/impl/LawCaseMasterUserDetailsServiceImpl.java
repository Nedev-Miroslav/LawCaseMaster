package com.example.lawcasemaster.service.impl;
import com.example.lawcasemaster.model.entity.Role;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.model.enums.RoleType;
import com.example.lawcasemaster.repository.UserRepository;
import com.example.lawcasemaster.service.LawCaseMasterUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LawCaseMasterUserDetailsServiceImpl implements LawCaseMasterUserDetailsService {
    private final UserRepository userRepository;


    public LawCaseMasterUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(this::mapToDetails)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + username + " not found!"));
    }
    private UserDetails mapToDetails(User user) {

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(Role::getRoleType).map(LawCaseMasterUserDetailsServiceImpl::mapped).toList())
                .disabled(false)
                .build();
    }

    private static GrantedAuthority mapped(RoleType role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}


