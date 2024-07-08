package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.AddCaseDTO;
import com.example.lawcasemaster.model.dto.AddClientDTO;
import com.example.lawcasemaster.model.entity.Case;

import java.util.List;

public interface CaseService {
    boolean createCase(AddCaseDTO data);

    List<Case> getAllMyCases();

    void deleteCase(Long id);
}

