package com.example.lawcasemaster.repository;

import com.example.lawcasemaster.model.entity.CourtSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtSessionRepository extends JpaRepository<CourtSession, Long> {



  @Query("SELECT cs FROM CourtSession cs WHERE cs.aCase.assignedLawyer.id = :lawyerId")
  List<CourtSession> findAllByACase_AssignedLawyer_Id(@Param("lawyerId") long id);


}
