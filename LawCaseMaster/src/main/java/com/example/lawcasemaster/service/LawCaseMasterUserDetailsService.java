package com.example.lawcasemaster.service;


import com.example.lawcasemaster.model.entity.UserEntity;
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

        return User.withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(List.of()) /*TODO*/
                .disabled(false)
                .build();
    }
}


