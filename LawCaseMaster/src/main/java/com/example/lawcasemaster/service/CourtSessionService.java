package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.AddCourtSessionDTO;
import com.example.lawcasemaster.model.entity.CourtSession;

import java.util.List;

public interface CourtSessionService {


    boolean create(AddCourtSessionDTO data);

    List<CourtSession> getAllMyCourtSessions();

    void deleteCourtSession(Long id);

    List<CourtSession> getPastSessions();
    void deletePastSessions();
}
