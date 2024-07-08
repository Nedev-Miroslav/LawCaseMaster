package com.example.lawcasemaster.model.dto;

import com.example.lawcasemaster.model.enums.CaseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class AddCaseDTO {

    @NotBlank(message = "Please enter case number!")
    private String caseNumber;

    private String description;

    @NotBlank(message = "Please enter client's email!")
    private String clientEmail;

    @NotNull(message = "Please enter date of creation!")
    @PastOrPresent(message = "Please enter today's or past date")
    private LocalDate createdAt;

    private CaseType caseType;

    public AddCaseDTO() {
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public CaseType getCaseType() {
        return caseType;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }
}
