package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.LoggedUserProfileDTO;
import com.example.lawcasemaster.model.dto.UserRegistrationDTO;
import com.example.lawcasemaster.model.entity.Role;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.model.enums.RoleType;
import com.example.lawcasemaster.repository.RoleRepository;
import com.example.lawcasemaster.repository.UserRepository;
import com.example.lawcasemaster.service.LoggedUserService;
import com.example.lawcasemaster.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUserService loggedUserService;


    public UserServiceImpl(UserRepository userRepository, RoleRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, LoggedUserService loggedUserService) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.loggedUserService = loggedUserService;
    }

    @Override
    public boolean registerUser(UserRegistrationDTO data) {
        Optional<User> existingUser = userRepository.findByUsername(data.getUsername());
        if(existingUser.isPresent()) {
            return false;
        }

        User mappedEntity = modelMapper.map(data, User.class);
        Role role = repository.findByRoleType(RoleType.LAWYER);

        mappedEntity.setPassword(passwordEncoder.encode(data.getPassword()));
        mappedEntity.getRoles().add(role);


        userRepository.save(mappedEntity);
        return true;

    }

    @Override
    public LoggedUserProfileDTO getProfileData() {
        return modelMapper.map(loggedUserService.getUser(), LoggedUserProfileDTO.class);
    }
}
