package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddCaseDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.Client;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.CaseRepository;
import com.example.lawcasemaster.repository.ClientRepository;
import com.example.lawcasemaster.service.CaseService;
import com.example.lawcasemaster.service.LoggedUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CaseServiceImpl implements CaseService {
    private final CaseRepository caseRepository;
    private final ClientRepository clientRepository;
    private final LoggedUserService loggedUserService;
    private final ModelMapper modelMapper;

    public CaseServiceImpl(CaseRepository caseRepository, ClientRepository clientRepository, LoggedUserService loggedUserService, ModelMapper modelMapper) {
        this.caseRepository = caseRepository;
        this.clientRepository = clientRepository;
        this.loggedUserService = loggedUserService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean checkIfClientExist(AddCaseDTO data) {
        Optional<Client> client = clientRepository.findByEmail(data.getClientEmail());
        if (client.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean createCase(AddCaseDTO data) {
        Optional<Case> byCaseNumber = caseRepository.findByCaseNumber(data.getCaseNumber());

        if (byCaseNumber.isPresent()) {
            return false;
        }

        Case aCase = this.modelMapper.map(data, Case.class);
        User user = loggedUserService.getUser();
        aCase.setAssignedLawyer(user);

        Optional<Client> client = clientRepository.findByEmail(data.getClientEmail());
        if(client.isPresent()){
            Client currentClient = client.get();
            aCase.setClient(currentClient);
            caseRepository.saveAndFlush(aCase);

        }

        return true;
    }

    @Override
    public List<Case> getAllMyCases() {
        User user = loggedUserService.getUser();

        return caseRepository.findAllByAssignedLawyer_Id(user.getId());
    }

    @Override
    public void deleteCase(Long id) {
        User user = loggedUserService.getUser();
        Optional<Case> caseToRemove =  caseRepository.findByIdAndAssignedLawyer_Id(id, user.getId());

        if(caseToRemove.isPresent()){
            caseRepository.deleteById(id);
        }
    }
}
