package com.example.lawcasemaster.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "documents")
public class Document extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(name="incoming_number", unique = true, nullable = false)
    private String incomingNumber;

    @Column(name = "add_document", columnDefinition = "LONGTEXT")
    private String addDocument;


    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case caseFile;

    public Document() {
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

    public String getAddDocument() {
        return addDocument;
    }

    public void setAddDocument(String addDocument) {
        this.addDocument = addDocument;
    }

    public Case getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(Case caseFile) {
        this.caseFile = caseFile;
    }
}
