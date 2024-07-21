package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.entity.Court;
import com.example.lawcasemaster.repository.CourtRepository;
import com.example.lawcasemaster.service.CourtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;

    public CourtServiceImpl(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    @Override
    public List<Court> getAllCourts() {
        return courtRepository.findAll();
    }
}