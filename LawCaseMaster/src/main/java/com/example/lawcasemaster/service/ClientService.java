package com.example.lawcasemaster.service;

import com.example.lawcasemaster.model.dto.AddClientDTO;
import com.example.lawcasemaster.model.entity.Client;

import java.util.List;

public interface ClientService {
    boolean create(AddClientDTO data);

    List<Client> getAllMyClients();

    void deleteClient(Long id);
}
