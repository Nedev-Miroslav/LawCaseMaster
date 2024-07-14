package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {

    boolean createDocument(AddDocumentDTO data, MultipartFile file) throws IOException;

    boolean checkValidCaseNumberInput(AddDocumentDTO data);


    boolean existDock(AddDocumentDTO data);
}
