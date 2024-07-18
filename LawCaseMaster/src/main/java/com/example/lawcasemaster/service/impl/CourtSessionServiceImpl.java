package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.repository.CourtSessionRepository;
import com.example.lawcasemaster.service.CourtSessionService;
import org.springframework.stereotype.Service;

@Service
public class CourtSessionServiceImpl implements CourtSessionService {

    private final CourtSessionRepository courtSessionRepository;

    public CourtSessionServiceImpl(CourtSessionRepository courtSessionRepository) {
        this.courtSessionRepository = courtSessionRepository;
    }
}
