package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.CourtRequestDTO;
import com.example.lawcasemaster.model.dto.CourtResponseDTO;
import com.example.lawcasemaster.model.entity.Court;

import java.util.List;

public interface CourtService {
    List<CourtResponseDTO> getAllCourts();
    CourtResponseDTO addCourt(CourtRequestDTO courtRequestDTO);
    void deleteCourt(Long id);
}
