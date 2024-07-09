package com.example.lawcasemaster.repository;

import com.example.lawcasemaster.model.dto.AddDocumentDTO;
import com.example.lawcasemaster.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByIncomingNumber(String incomingNumber);
}
