package com.example.lawcasemaster.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {

    @Size(min = 5, max = 30, message = "Username length must be between 5 and 30 characters!")
    @NotNull
    private String username;
    @Size(min = 5, max = 30, message = "Password length must be between 5 and 30 characters!")
    @NotNull
    private String password;

    public UserLoginDTO() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}