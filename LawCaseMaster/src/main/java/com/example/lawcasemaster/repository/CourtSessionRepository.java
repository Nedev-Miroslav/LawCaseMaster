package com.example.lawcasemaster.repository;

import com.example.lawcasemaster.model.entity.CourtSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourtSessionRepository extends JpaRepository<CourtSession, Long> {

  @Query("SELECT cs FROM CourtSession cs WHERE cs.aCase.assignedLawyer.id = :lawyerId ORDER BY cs.date")
  List<CourtSession> findAllByACase_AssignedLawyer_Id(@Param("lawyerId") long id);

  @Query("SELECT cs FROM CourtSession cs WHERE cs.id = :id AND cs.aCase.assignedLawyer.id = :id1")
  Optional<CourtSession> findByIdAndCaseFile_AssignedLawyer_Id(Long id, long id1);

  List<CourtSession> findByDateBefore(LocalDateTime now);



}
