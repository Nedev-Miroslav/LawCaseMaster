package com.example.lawcasemaster.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "documents")
public class Document extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Instant uploadedAt;

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

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Case getCaseFile() {
        return caseFile;
    }

    public void setCaseFile(Case caseFile) {
        this.caseFile = caseFile;
    }
}
