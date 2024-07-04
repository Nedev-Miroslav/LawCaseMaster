package com.example.lawcasemaster.model.dto;

import com.example.lawcasemaster.model.entity.Role;

import java.util.List;

public class LoggedUserProfileDTO {


    private String username;

    private String email;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    private String phoneNumber;
    private List<Role> roles;


    public LoggedUserProfileDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

