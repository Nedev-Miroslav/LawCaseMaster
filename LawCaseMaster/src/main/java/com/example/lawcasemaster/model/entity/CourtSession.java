package com.example.lawcasemaster.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "court_sessions")
public class CourtSession extends BaseEntity{

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private String location;

    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private Case aCase;

    public CourtSession() {
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
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

    public Case getaCase() {
        return aCase;
    }

    public void setaCase(Case aCase) {
        this.aCase = aCase;
    }
}
