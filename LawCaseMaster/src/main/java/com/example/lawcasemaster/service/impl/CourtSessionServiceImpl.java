package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddCourtSessionDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.CourtSession;
import com.example.lawcasemaster.repository.CaseRepository;
import com.example.lawcasemaster.repository.CourtSessionRepository;
import com.example.lawcasemaster.service.CourtSessionService;
import com.example.lawcasemaster.service.LoggedUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourtSessionServiceImpl implements CourtSessionService {

    private final CourtSessionRepository courtSessionRepository;
    private final CaseRepository caseRepository;
    private final LoggedUserService loggedUserService;

    private final ModelMapper modelMapper;

    public CourtSessionServiceImpl(CourtSessionRepository courtSessionRepository, CaseRepository caseRepository, LoggedUserService loggedUserService, ModelMapper modelMapper) {
        this.courtSessionRepository = courtSessionRepository;
        this.caseRepository = caseRepository;
        this.loggedUserService = loggedUserService;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean create(AddCourtSessionDTO data) {
      Optional<Case> optionalCase = caseRepository.findByCaseNumber(data.getCaseNumber());

      if(optionalCase.isEmpty()) {
          return false;
      }

        CourtSession courtSession = this.modelMapper.map(data, CourtSession.class);
        courtSession.setaCase(optionalCase.get());
        courtSessionRepository.save(courtSession);

      return true;
    }
}
