package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import com.example.lawcasemaster.model.entity.Case;
import com.example.lawcasemaster.model.entity.Document;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.CaseRepository;
import com.example.lawcasemaster.repository.DocumentRepository;
import com.example.lawcasemaster.service.DocumentService;
import com.example.lawcasemaster.service.LoggedUserService;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final CaseRepository caseRepository;
    private final ModelMapper modelMapper;
    private final LoggedUserService loggedUserService;

    public DocumentServiceImpl(DocumentRepository documentRepository, CaseRepository caseRepository, ModelMapper modelMapper, LoggedUserService loggedUserService) {
        this.documentRepository = documentRepository;
        this.caseRepository = caseRepository;
        this.modelMapper = modelMapper;
        this.loggedUserService = loggedUserService;
    }

    @Override
    public boolean checkValidCaseNumberInput(AddDocumentDTO data) {
        Optional<Case> currentCase = caseRepository.findByCaseNumber(data.getCaseNumber());
        return currentCase.isPresent();
    }

    @Override
    public boolean existDock(AddDocumentDTO data) {
        Optional<Document> optDoc = documentRepository.findByIncomingNumber(data.getIncomingNumber());
        return optDoc.isEmpty();
    }


    @Override
    public boolean createDocument(AddDocumentDTO data, MultipartFile file) throws IOException {
        Optional<Document> optDoc = documentRepository.findByIncomingNumber(data.getIncomingNumber());
        if (optDoc.isPresent()){
            return false;
        }


        if (file == null || file.isEmpty()) {
           // throw new IllegalArgumentException("File is empty!");
            return false;
        }


        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        } else {
//            throw new IllegalArgumentException("No such customer added!");
            return false;
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

    @Override
    public List<Document> getAllMyDocuments() {
        User user = loggedUserService.getUser();

        return documentRepository.findAllByCaseFile_AssignedLawyer_Id(user.getId());
    }

    @Override
    public void deleteDocument(Long id) {
        User user = loggedUserService.getUser();
        Optional<Document> documentToRemove =  documentRepository.findByIdAndCaseFile_AssignedLawyer_Id(id, user.getId());

        if(documentToRemove.isPresent()){
            documentRepository.deleteById(id);
        }
    }


}
