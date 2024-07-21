package com.example.lawcasemaster.repository;

import com.example.lawcasemaster.model.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {


    @Query("SELECT c FROM Court c ORDER BY c.name")
    List<Court> findAll();

}
// @Query("SELECT cs FROM CourtSession cs WHERE cs.aCase.assignedLawyer.id = :lawyerId ORDER BY cs.date")