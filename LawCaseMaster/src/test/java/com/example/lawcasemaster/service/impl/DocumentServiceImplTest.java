package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.Document;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.CaseRepository;
import com.example.lawcasemaster.repository.DocumentRepository;
import com.example.lawcasemaster.service.LoggedUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LoggedUserService loggedUserService;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Test
    void testCheckValidCaseNumberInput() {
        AddDocumentDTO data = new AddDocumentDTO();
        data.setCaseNumber("validCaseNumber");

        Case validCase = new Case();
        when(caseRepository.findByCaseNumber(data.getCaseNumber())).thenReturn(Optional.of(validCase));

        boolean result = documentService.checkValidCaseNumberInput(data);

        Assertions.assertTrue(result);
    }

    @Test
    void testCheckInvalidCaseNumberInput() {
        AddDocumentDTO data = new AddDocumentDTO();
        data.setCaseNumber("invalidCaseNumber");

        when(caseRepository.findByCaseNumber(data.getCaseNumber())).thenReturn(Optional.empty());

        boolean result = documentService.checkValidCaseNumberInput(data);

        Assertions.assertFalse(result);
    }

    @Test
    void testExistDocument() {
        AddDocumentDTO data = new AddDocumentDTO();
        data.setIncomingNumber("existingIncomingNumber");

        Document existingDocument = new Document();
        when(documentRepository.findByIncomingNumber(data.getIncomingNumber())).thenReturn(Optional.of(existingDocument));

        boolean result = documentService.existDock(data);

        Assertions.assertFalse(result);
    }

    @Test
    void testNotExistDocument() {
        AddDocumentDTO data = new AddDocumentDTO();
        data.setIncomingNumber("nonExistingIncomingNumber");

        when(documentRepository.findByIncomingNumber(data.getIncomingNumber())).thenReturn(Optional.empty());

        boolean result = documentService.existDock(data);

        Assertions.assertTrue(result);
    }

    @Test
    void testCreateDocument() throws IOException {
        AddDocumentDTO data = new AddDocumentDTO();
        data.setIncomingNumber("newIncomingNumber");
        data.setCaseNumber("validCaseNumber");

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("document.pdf");

        Case validCase = new Case();
        when(caseRepository.findByCaseNumber(data.getCaseNumber())).thenReturn(Optional.of(validCase));

        Document documentToInsert = new Document();
        when(modelMapper.map(data, Document.class)).thenReturn(documentToInsert);

        // Мокираме създаването на директория и пътя на файла
        Path tempDir = Files.createTempDirectory("uploads");
        Path destinationFile = tempDir.resolve(UUID.randomUUID().toString() + ".pdf");

        // Мокираме InputStream на файла
        InputStream inputStream = mock(InputStream.class);
        when(file.getInputStream()).thenReturn(inputStream);

        // Създаваме файла на мока
        Files.copy(inputStream, destinationFile);

        boolean result = documentService.createDocument(data, file);

        Assertions.assertTrue(result);
        verify(documentRepository).save(any(Document.class));

        // Изтриваме тестовата директория след теста
        Files.deleteIfExists(destinationFile);
        Files.deleteIfExists(tempDir);
    }

    @Test
    void testEmptyFileDocument() throws IOException {
        AddDocumentDTO data = new AddDocumentDTO();
        data.setIncomingNumber("newIncomingNumber");

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        boolean result = documentService.createDocument(data, file);

        Assertions.assertFalse(result);
        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    void testInvalidFilename() throws IOException {
        AddDocumentDTO data = new AddDocumentDTO();
        data.setIncomingNumber("newIncomingNumber");

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("document");

        boolean result = documentService.createDocument(data, file);

        Assertions.assertFalse(result);
        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    void testGetAllMyDocuments() {
        User user = new User();
        user.setId(1L);

        when(loggedUserService.getUser()).thenReturn(user);

        List<Document> documents = List.of(new Document(), new Document());
        when(documentRepository.findAllByCaseFile_AssignedLawyer_Id(user.getId())).thenReturn(documents);

        List<Document> result = documentService.getAllMyDocuments();

        Assertions.assertEquals(documents, result);
    }

    @Test
    void testDeleteDocument() {
        User user = new User();
        user.setId(1L);

        when(loggedUserService.getUser()).thenReturn(user);

        Document document = new Document();
        when(documentRepository.findByIdAndCaseFile_AssignedLawyer_Id(1L, user.getId())).thenReturn(Optional.of(document));

        documentService.deleteDocument(1L);

        verify(documentRepository).deleteById(1L);
    }

    @Test
    void testDeleteNotExistDocument() {
        User user = new User();
        user.setId(1L);

        when(loggedUserService.getUser()).thenReturn(user);

        when(documentRepository.findByIdAndCaseFile_AssignedLawyer_Id(1L, user.getId())).thenReturn(Optional.empty());

        documentService.deleteDocument(1L);

        verify(documentRepository, never()).deleteById(1L);
    }
}
