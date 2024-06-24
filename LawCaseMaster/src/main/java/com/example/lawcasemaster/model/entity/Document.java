package com.example.lawcasemaster.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "documents")
public class Document extends BaseEntity{

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Instant uploadedAt;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case aCase;

    public Document() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Case getaCase() {
        return aCase;
    }

    public void setaCase(Case aCase) {
        this.aCase = aCase;
    }
}
