package com.example.lawcasemaster.repository;

import com.example.lawcasemaster.model.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    Optional<Case> findByCaseNumber(String caseNumber);

    List<Case> findAllByAssignedLawyer_Id(long id);


    Optional<Case> findByIdAndAssignedLawyer_Id(Long id, long id1);
}
