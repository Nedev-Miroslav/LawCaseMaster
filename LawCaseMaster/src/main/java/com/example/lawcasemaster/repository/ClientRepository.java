package com.example.lawcasemaster.repository;

import com.example.lawcasemaster.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

    List<Client> findAllByUser_Id(Long id);

    Optional<Client> findByIdAndUser_id(Long id, long id1);
}
