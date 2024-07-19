package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.LoggedUserProfileDTO;
import com.example.lawcasemaster.model.dto.UserRegistrationDTO;
import com.example.lawcasemaster.model.entity.User;

import java.util.List;

public interface UserService {
    boolean registerUser(UserRegistrationDTO data);

    LoggedUserProfileDTO getProfileData();

    List<User> getAllUsers();

}
