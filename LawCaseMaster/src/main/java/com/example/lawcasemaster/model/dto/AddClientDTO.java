package com.example.lawcasemaster.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AddClientDTO {

    @NotBlank(message = "Please enter client's First name")
    private String firstName;

    @NotBlank(message = "Please enter client's Last name")
    private String lastName;

    @NotBlank(message = "Please enter client's Email")
    @Email
    private String email;

    @NotBlank(message = "Please enter client's Phone number")
    private String phoneNumber;

    public AddClientDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
