package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import com.example.lawcasemaster.model.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    boolean createDocument(AddDocumentDTO data, MultipartFile file) throws IOException;

    boolean checkValidCaseNumberInput(AddDocumentDTO data);


    boolean existDock(AddDocumentDTO data);

    List<Document> getAllMyDocuments();

    void deleteDocument(Long id);
}
