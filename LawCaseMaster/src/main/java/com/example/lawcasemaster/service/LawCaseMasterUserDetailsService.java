package com.example.lawcasemaster.service;


import com.example.lawcasemaster.model.entity.Role;
import com.example.lawcasemaster.model.entity.UserEntity;
import com.example.lawcasemaster.model.enums.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.example.lawcasemaster.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class LawCaseMasterUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    public LawCaseMasterUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository
                .findByUsername(username)
                .map(LawCaseMasterUserDetailsService::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + username + " not found!"));
    }
    private static UserDetails map(UserEntity userEntity) {

        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(Role::getRoleType).map(LawCaseMasterUserDetailsService::mapped).toList()) /*TODO*/
                .disabled(false)
                .build();
    }

    private static GrantedAuthority mapped(RoleType role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}


