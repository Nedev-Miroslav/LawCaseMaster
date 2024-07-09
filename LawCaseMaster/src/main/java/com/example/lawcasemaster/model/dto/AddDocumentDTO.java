package com.example.lawcasemaster.model.dto;

public class AddDocumentDTO {

    private String name;
    private String incomingNumber;
    private String addDocuments;
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

    public String getAddDocuments() {
        return addDocuments;
    }

    public void setAddDocuments(String addDocuments) {
        this.addDocuments = addDocuments;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }
}
