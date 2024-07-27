package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddCourtSessionDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.CourtSession;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.CaseRepository;
import com.example.lawcasemaster.repository.CourtSessionRepository;
import com.example.lawcasemaster.service.LoggedUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourtSessionServiceImplTest {

    @Mock
    private CourtSessionRepository mockCourtSessionRepository;

    @Mock
    private CaseRepository mockCaseRepository;

    @Mock
    private LoggedUserService mockLoggedUserService;

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private CourtSessionServiceImpl courtSessionService;

    @BeforeEach
    public void setup() {
        mockCourtSessionRepository.deleteAll();

    }

    @Test
    public void testCreateCourtSession() {
        AddCourtSessionDTO dto = new AddCourtSessionDTO();
        dto.setLocation("VTRS");
        dto.setDate(LocalDateTime.now());
        dto.setNotes("test notes");
        dto.setCaseNumber("GRD");
        dto.setCaseNumber("1");

        Case aCase = new Case();
        when(mockCaseRepository.findByCaseNumber("1")).thenReturn(Optional.of(aCase));
        CourtSession courtSession = new CourtSession();
        when(mockModelMapper.map(dto, CourtSession.class)).thenReturn(courtSession);

        boolean result = courtSessionService.create(dto);

        Assertions.assertTrue(result);
        verify(mockCourtSessionRepository, times(1)).save(any(CourtSession.class));

    }

    @Test
    public void testFailCreationOfCourtSessionMissingCase() {
        AddCourtSessionDTO dto = new AddCourtSessionDTO();
        dto.setLocation("VTRS");
        dto.setDate(LocalDateTime.now());
        dto.setNotes("test notes");
        dto.setCaseNumber("GRD");
        dto.setCaseNumber("1");

        when(mockCaseRepository.findByCaseNumber("1")).thenReturn(Optional.empty());

        boolean result = courtSessionService.create(dto);

        Assertions.assertFalse(result);
        verify(mockCourtSessionRepository, never()).save(any(CourtSession.class));
    }


    @Test
    public void testGetAllMyCourtSessions() {
        User user = new User();
        user.setId(1L);
        when(mockLoggedUserService.getUser()).thenReturn(user);
        CourtSession session1 = new CourtSession();
        CourtSession session2 = new CourtSession();
        when(mockCourtSessionRepository.findAllByACase_AssignedLawyer_Id(1L)).thenReturn(Arrays.asList(session1, session2));

        List<CourtSession> sessions = courtSessionService.getAllMyCourtSessions();

        Assertions.assertEquals(2, sessions.size());
        Assertions.assertEquals(session1, sessions.get(0));
        Assertions.assertEquals(session2, sessions.get(1));
    }

    @Test
    public void testDeleteCourtSession_Success() {
        User user = new User();
        user.setId(1L);
        when(mockLoggedUserService.getUser()).thenReturn(user);
        CourtSession courtSession = new CourtSession();
        when(mockCourtSessionRepository.findByIdAndCaseFile_AssignedLawyer_Id(1L, 1L)).thenReturn(Optional.of(courtSession));

        courtSessionService.deleteCourtSession(1L);

        verify(mockCourtSessionRepository, times(1)).deleteById(1L);
    }
}
