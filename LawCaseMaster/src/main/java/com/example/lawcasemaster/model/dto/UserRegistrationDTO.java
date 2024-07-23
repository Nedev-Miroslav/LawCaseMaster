package com.example.lawcasemaster.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRegistrationDTO {
    @NotEmpty(message = "")
    @Size(min = 5, max = 30, message = "{username.length}")
    private String username;

    @NotBlank(message = "{email.enter}")
    @Email
    private String email;

    @NotEmpty(message = "{valid.phone.number}")
    private String phoneNumber;
    @NotEmpty(message = "")
    @Size(min = 5, max = 30, message = "{password.length}")
    private String password;
    @NotEmpty(message = "")
    @Size(min = 5, max = 30, message = "{confirm.password.length}")
    private String confirmPassword;

    public UserRegistrationDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
