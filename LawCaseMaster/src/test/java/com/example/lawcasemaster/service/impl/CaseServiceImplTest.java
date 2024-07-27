package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddCaseDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.Client;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.CaseRepository;
import com.example.lawcasemaster.repository.ClientRepository;
import com.example.lawcasemaster.service.LoggedUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CaseServiceImplTest {

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private LoggedUserService loggedUserService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CaseServiceImpl caseService;

    @BeforeEach
    public void setup() {
        caseRepository.deleteAll();

    }

    @Test
    public void testCreateCase() {

        AddCaseDTO testData = new AddCaseDTO();
        testData.setCaseNumber("TestCaseNumber111");
        testData.setClientEmail("test@abv.bg");

        User loggedUser = new User();
        loggedUser.setId(1L);

        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setEmail("test@abv.bg");

        Case mappedCase = new Case();
        mappedCase.setCaseNumber("TestCaseNumber111");
        mappedCase.setClient(existingClient);
        mappedCase.setAssignedLawyer(loggedUser);

        when(loggedUserService.getUser()).thenReturn(loggedUser);
        when(clientRepository.findByEmail("test@abv.bg")).thenReturn(Optional.of(existingClient));
        when(modelMapper.map(testData, Case.class)).thenReturn(mappedCase);


        boolean result = caseService.createCase(testData);


        Assertions.assertTrue(result);
        verify(caseRepository, times(1)).saveAndFlush(mappedCase);
    }

    @Test
    public void testCheckIfClientExist() {

        AddCaseDTO testData = new AddCaseDTO();
        testData.setClientEmail("test@abv.bg");

        Client existingClient = new Client();
        existingClient.setEmail("test@abv.bg");

        when(clientRepository.findByEmail("test@abv.bg")).thenReturn(Optional.of(existingClient));


        boolean result = caseService.checkIfClientExist(testData);


        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckIfClientNotExist() {

        AddCaseDTO testData = new AddCaseDTO();
        testData.setClientEmail("test@abv.bg");

        when(clientRepository.findByEmail("test@abv.bg")).thenReturn(Optional.empty());


        boolean result = caseService.checkIfClientExist(testData);


        Assertions.assertFalse(result);
    }

    @Test
    public void testCreateCaseCaseNumberExists() {

        AddCaseDTO testData = new AddCaseDTO();
        testData.setCaseNumber("TestCaseNumber111");

        when(caseRepository.findByCaseNumber("TestCaseNumber111")).thenReturn(Optional.of(new Case()));


        boolean result = caseService.createCase(testData);


        Assertions.assertFalse(result);
    }


    @Test
    public void testGetAllMyCases() {

        User loggedUser = new User();
        loggedUser.setId(1L);

        List<Case> cases = new ArrayList<>();
        Case case1 = new Case();
        case1.setId(1L);
        case1.setAssignedLawyer(loggedUser);
        cases.add(case1);

        when(loggedUserService.getUser()).thenReturn(loggedUser);
        when(caseRepository.findAllByAssignedLawyer_Id(loggedUser.getId())).thenReturn(cases);


        List<Case> result = caseService.getAllMyCases();


        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void testDeleteCase() {

        Long caseId = 1L;
        User loggedUser = new User();
        loggedUser.setId(1L);

        Case existingCase = new Case();
        existingCase.setId(caseId);
        existingCase.setAssignedLawyer(loggedUser);

        when(loggedUserService.getUser()).thenReturn(loggedUser);
        when(caseRepository.findByIdAndAssignedLawyer_Id(caseId, loggedUser.getId())).thenReturn(Optional.of(existingCase));


        caseService.deleteCase(caseId);


        verify(caseRepository, times(1)).deleteById(caseId);
    }

    @Test
    public void testDeleteNotExistCase() {

        Long caseId = 1L;
        User loggedUser = new User();
        loggedUser.setId(1L);

        when(loggedUserService.getUser()).thenReturn(loggedUser);
        when(caseRepository.findByIdAndAssignedLawyer_Id(caseId, loggedUser.getId())).thenReturn(Optional.empty());


        caseService.deleteCase(caseId);


        verify(caseRepository, never()).deleteById(caseId);
    }
}