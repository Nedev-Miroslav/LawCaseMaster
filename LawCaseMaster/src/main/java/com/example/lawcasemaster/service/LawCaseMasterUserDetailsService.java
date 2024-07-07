package com.example.lawcasemaster.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LawCaseMasterUserDetailsService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException;
}
