package com.example.lawcasemaster.web;

import com.example.lawcasemaster.model.dto.AddCaseDTO;
import com.example.lawcasemaster.model.enums.CaseType;
import com.example.lawcasemaster.service.CaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CaseControllerIT{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService caseService;


    private AddCaseDTO addCaseDTO;

    @BeforeEach
    public void setUp() {
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


    @Test
    @WithMockUser(username = "user1", roles = {"LAWYER"})
    public void testGetAllCases() throws Exception {
        mockMvc.perform(get("/cases"))
                .andExpect(status().isOk())
                .andExpect(view().name("cases"))
                .andExpect(model().attributeExists("allMyCases"));

        verify(caseService, times(1)).getAllMyCases();
    }
}
