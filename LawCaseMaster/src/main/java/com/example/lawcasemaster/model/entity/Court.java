package com.example.lawcasemaster.model.entity;

import jakarta.persistence.Entity;

@Entity
public class Court extends BaseEntity{

    private String name;
    private String address;
    private String phoneNumber;

    public Court() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
