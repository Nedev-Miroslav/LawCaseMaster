package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.UserLoginDTO;
import com.example.lawcasemaster.model.dto.UserRegistrationDTO;
import com.example.lawcasemaster.model.entity.Role;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.model.enums.RoleType;
import com.example.lawcasemaster.repository.RoleRepository;
import com.example.lawcasemaster.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;



    public UserService(UserRepository userRepository, RoleRepository repository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

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

//    private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
//        UserEntity mappedEntity = modelMapper.map(userRegistrationDTO, UserEntity.class);
//        Role role = repository.findByRoleType(RoleType.LAWYER);
//
//        mappedEntity.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
//        mappedEntity.getRoles().add(role);
//        return mappedEntity;
//
//    }

    public boolean register(UserRegistrationDTO data) {
        Optional<User> existingUser = userRepository.findByUsernameOrEmail(data.getUsername(), data.getEmail());

        if(existingUser.isPresent()) {
            return false;
        }

        User user = this.modelMapper.map(existingUser, User.class);
        user.setPassword(passwordEncoder.encode(data.getPassword()));


        this.userRepository.save(user);


        return true;

    }

    public boolean login(UserLoginDTO data) {
        Optional<User> byUsername = userRepository.findByUsername(data.getUsername());

        if(byUsername.isEmpty()) {
            return false;
        }

        boolean passwordNotMatch = passwordEncoder
                .matches(data.getPassword(), byUsername.get().getPassword());

        if (!passwordNotMatch) {
            return false;
        }

        return true;
    }
}
