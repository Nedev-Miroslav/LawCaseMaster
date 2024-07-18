package com.example.lawcasemaster.model.entity;

import com.example.lawcasemaster.model.enums.CaseType;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cases")
public class Case extends BaseEntity{

    @Column(name = "case_number", nullable = false, unique = true)
    private String caseNumber;

    @Column
    private String description;


    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private CaseType caseType;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedLawyer;

    @OneToMany(mappedBy = "caseFile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents;

    @OneToMany(mappedBy = "aCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourtSession> courtSessions;


    public Case() {
        this.documents = new HashSet<>();
        this.courtSessions = new HashSet<>();
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getAssignedLawyer() {
        return assignedLawyer;
    }

    public void setAssignedLawyer(User assignedLawyer) {
        this.assignedLawyer = assignedLawyer;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    public Set<CourtSession> getCourtSessions() {
        return courtSessions;
    }

    public void setCourtSessions(Set<CourtSession> courtSessions) {
        this.courtSessions = courtSessions;

    }

}
