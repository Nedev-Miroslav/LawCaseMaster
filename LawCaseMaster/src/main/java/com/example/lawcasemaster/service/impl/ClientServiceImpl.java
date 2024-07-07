package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.dto.AddClientDTO;
import com.example.lawcasemaster.model.entity.Client;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.ClientRepository;
import com.example.lawcasemaster.service.ClientService;
import com.example.lawcasemaster.service.LoggedUserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final LoggedUserService loggedUserService;
    private final ModelMapper modelMapper;


    public ClientServiceImpl(ClientRepository clientRepository, LoggedUserService loggedUserService, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.loggedUserService = loggedUserService;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean create(AddClientDTO data) {
        Optional<Client> byEmail = clientRepository.findByEmail(data.getEmail());

        if (byEmail.isPresent()) {
            return false;
        }

        Client client = this.modelMapper.map(data, Client.class);
        User user = loggedUserService.getUser();
        client.setUser(user);

        clientRepository.save(client);


        return true;


    }

    @Override
    public List<Client> getAllMyClients() {
        User user = loggedUserService.getUser();

       return clientRepository.findAllByUser_Id(user.getId());



    }
}
