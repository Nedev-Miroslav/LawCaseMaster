package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.CourtRequestDTO;
import com.example.lawcasemaster.model.dto.CourtResponseDTO;
import com.example.lawcasemaster.model.entity.Court;
import com.example.lawcasemaster.repository.CourtRepository;
import com.example.lawcasemaster.service.CourtService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;

    public CourtServiceImpl(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }
    @Override
    public List<CourtResponseDTO> getAllCourts() {
        return courtRepository.findAll().stream()
                .map(court -> new CourtResponseDTO(court.getId(), court.getName(), court.getAddress(), court.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public CourtResponseDTO addCourt(CourtRequestDTO courtRequestDTO) {
        Court court = new Court();
        court.setName(courtRequestDTO.getName());
        court.setAddress(courtRequestDTO.getAddress());
        court.setPhoneNumber(courtRequestDTO.getPhoneNumber());
        court = courtRepository.save(court);
        return new CourtResponseDTO(court.getId(), court.getName(), court.getAddress(), court.getPhoneNumber());
    }

    @Override
    public void deleteCourt(Long id) {
        courtRepository.deleteById(id);
    }
}
