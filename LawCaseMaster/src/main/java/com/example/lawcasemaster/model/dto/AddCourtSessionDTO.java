package com.example.lawcasemaster.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AddCourtSessionDTO {
    @NotNull(message = "Please enter day and time of court session!")
    private LocalDateTime date;
    @NotBlank(message = "Please enter location - court!")
    private String location;

    private String notes;
    @NotBlank(message = "Please enter case number!")
    private String caseNumber;

    public AddCourtSessionDTO() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }
}
