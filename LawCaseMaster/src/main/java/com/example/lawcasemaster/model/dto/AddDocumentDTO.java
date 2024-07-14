package com.example.lawcasemaster.model.dto;

import jakarta.validation.constraints.NotBlank;

public class AddDocumentDTO {

    @NotBlank(message = "Please enter document name!")
    private String name;
    @NotBlank(message = "Please enter incoming number!")
    private String incomingNumber;
    @NotBlank(message = "Please enter case number!")
    private String caseNumber;

    public AddDocumentDTO() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncomingNumber() {
        return incomingNumber;
    }

    public void setIncomingNumber(String incomingNumber) {
        this.incomingNumber = incomingNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }
}
