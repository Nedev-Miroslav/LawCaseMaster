package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.AddCaseDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.model.enums.CaseType;
import com.example.lawcasemaster.service.CaseService;
import com.example.lawcasemaster.service.impl.LoggedUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CaseControllerIT{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService caseService;

//    @MockBean
//    private LoggedUserServiceImpl mockLoggedUserService;

//    @Captor
//    ArgumentCaptor<AddCaseDTO> caseCaptor;

    private AddCaseDTO addCaseDTO;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);
        addCaseDTO = new AddCaseDTO();
        addCaseDTO.setCaseNumber("12345");
        addCaseDTO.setDescription("Test Description");
        addCaseDTO.setClientEmail("test@example.com");
        addCaseDTO.setCreatedAt(LocalDate.now());
        addCaseDTO.setCaseType(CaseType.CIVIL);
    }

    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testAddCaseGet() throws Exception {
        mockMvc.perform(get("/add-case"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-case"))
                .andExpect(model().attributeExists("caseData"))
                .andExpect(model().attribute("caseTypes", CaseType.values()));
    }

//    @Test
//    @WithMockUser(username = "user1", roles = {"LAWYER"})
//    public void testAddCasePost_Success() throws Exception {
//        when(caseService.createCase(any(AddCaseDTO.class))).thenReturn(true);
//        when(caseService.checkIfClientExist(any(AddCaseDTO.class))).thenReturn(true);
//
//        mockMvc.perform(post("/add-case")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("caseNumber", addCaseDTO.getCaseNumber())
//                        .param("description", addCaseDTO.getDescription())
//                        .param("clientEmail", addCaseDTO.getClientEmail())
//                        .param("createdAt", addCaseDTO.getCreatedAt().toString())
//                        .param("caseType", addCaseDTO.getCaseType().toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/cases"));
//
//        verify(caseService, times(1)).createCase(caseCaptor.capture());
//        AddCaseDTO capturedCase = caseCaptor.getValue();
//        assertEquals(addCaseDTO.getCaseNumber(), capturedCase.getCaseNumber());
//        assertEquals(addCaseDTO.getDescription(), capturedCase.getDescription());
//        assertEquals(addCaseDTO.getClientEmail(), capturedCase.getClientEmail());
//        assertEquals(addCaseDTO.getCreatedAt(), capturedCase.getCreatedAt());
//        assertEquals(addCaseDTO.getCaseType(), capturedCase.getCaseType());
//    }
//
//    @Test
//    @WithMockUser(username = "user1", roles = {"LAWYER"})
//    public void testAddCasePost_BindingErrors() throws Exception {
//        mockMvc.perform(post("/add-case")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("caseNumber", "") // Invalid case number
//                        .param("description", addCaseDTO.getDescription())
//                        .param("clientEmail", addCaseDTO.getClientEmail())
//                        .param("createdAt", addCaseDTO.getCreatedAt().toString())
//                        .param("caseType", addCaseDTO.getCaseType().toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-case"))
//                .andExpect(flash().attributeExists("caseData"))
//                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.caseData"));
//
//        verify(caseService, never()).createCase(any(AddCaseDTO.class));
//    }
//
//    @Test
//    @WithMockUser(username = "user1", roles = {"LAWYER"})
//    public void testAddCasePost_ClientNotExist() throws Exception {
//        when(caseService.createCase(any(AddCaseDTO.class))).thenReturn(true);
//        when(caseService.checkIfClientExist(any(AddCaseDTO.class))).thenReturn(false);
//
//        mockMvc.perform(post("/add-case")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("caseNumber", addCaseDTO.getCaseNumber())
//                        .param("description", addCaseDTO.getDescription())
//                        .param("clientEmail", addCaseDTO.getClientEmail())
//                        .param("createdAt", addCaseDTO.getCreatedAt().toString())
//                        .param("caseType", addCaseDTO.getCaseType().toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-case"))
//                .andExpect(flash().attributeExists("missClient"));
//
//        verify(caseService, times(1)).createCase(any(AddCaseDTO.class));
//        verify(caseService, times(1)).checkIfClientExist(any(AddCaseDTO.class));
//    }
//
//    @Test
//    @WithMockUser(username = "user1", roles = {"LAWYER"})
//    public void testAddCasePost_CaseCreationFailed() throws Exception {
//        when(caseService.createCase(any(AddCaseDTO.class))).thenReturn(false);
//
//        mockMvc.perform(post("/add-case")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("caseNumber", addCaseDTO.getCaseNumber())
//                        .param("description", addCaseDTO.getDescription())
//                        .param("clientEmail", addCaseDTO.getClientEmail())
//                        .param("createdAt", addCaseDTO.getCreatedAt().toString())
//                        .param("caseType", addCaseDTO.getCaseType().toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/add-case"))
//                .andExpect(flash().attributeExists("caseError"));
//
//        verify(caseService, times(1)).createCase(any(AddCaseDTO.class));
//    }

    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testGetAllCases() throws Exception {
        mockMvc.perform(get("/cases"))
                .andExpect(status().isOk())
                .andExpect(view().name("cases"))
                .andExpect(model().attributeExists("allMyCases"));

        verify(caseService, times(1)).getAllMyCases();
    }

//    @Test
//    @WithMockUser(username = "user1", roles = {"LAWYER"})
//    public void testDeleteCase() throws Exception {
//        Long caseId = 1L;
//
//        mockMvc.perform(delete("/cases/{id}", caseId))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/cases"));
//
//        verify(caseService, times(1)).deleteCase(caseId);
//    }
}
