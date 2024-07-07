package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.LoggedUserProfileDTO;
import com.example.lawcasemaster.model.dto.UserRegistrationDTO;

public interface UserService {
    boolean registerUser(UserRegistrationDTO data);

    LoggedUserProfileDTO getProfileData();
}
