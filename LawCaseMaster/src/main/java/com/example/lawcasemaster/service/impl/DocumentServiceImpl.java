package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.Document;
import com.example.lawcasemaster.repository.CaseRepository;
import com.example.lawcasemaster.repository.DocumentRepository;
import com.example.lawcasemaster.service.DocumentService;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final CaseRepository caseRepository;
    private final ModelMapper modelMapper;

    public DocumentServiceImpl(DocumentRepository documentRepository, CaseRepository caseRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.caseRepository = caseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean createDocument(AddDocumentDTO data, MultipartFile file) throws IOException {
        Optional<Document> optDoc = documentRepository.findByIncomingNumber(data.getIncomingNumber());
        if (optDoc.isPresent()){
            return false;
        }



        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty!");
        }


        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        } else {
            throw new IllegalArgumentException("No such customer added!");
        }


        String uniqueFilename = UUID.randomUUID().toString() + extension;

        Path uploadDirectory = Paths.get("src", "main", "resources", "uploads")
                .normalize()
                .toAbsolutePath();

        Files.createDirectories(uploadDirectory);

        Path destinationFile = uploadDirectory.resolve(uniqueFilename);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        Optional<Case> currentCase = caseRepository.findByCaseNumber(data.getCaseNumber());
        if (currentCase.isEmpty()) {
            return false;
        }

        Document toInsert = modelMapper.map(data, Document.class);

        toInsert.setAddDocument(destinationFile.toString());
        toInsert.setCaseFile(currentCase.get());

        documentRepository.save(toInsert);
        return true;
    }

}
