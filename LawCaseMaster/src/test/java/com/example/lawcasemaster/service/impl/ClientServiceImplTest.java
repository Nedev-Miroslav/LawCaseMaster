package com.example.lawcasemaster.service.impl;

import com.example.lawcasemaster.model.entity.Client;
import com.example.lawcasemaster.model.entity.User;
import com.example.lawcasemaster.repository.ClientRepository;
import com.example.lawcasemaster.service.LoggedUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {


    @Mock
    private ClientRepository mockClientRepository;

    @Mock
    private LoggedUserService mockLoggedUserService;

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private ClientServiceImpl clientService;


    @BeforeEach
    void setUp() {
        clientService = new ClientServiceImpl(mockClientRepository,
                mockLoggedUserService, mockModelMapper);

    }

    @Test
    void testGetAllMyClients() {
        User user = new User();
        user.setId(1L);

        when(mockLoggedUserService.getUser()).thenReturn(user);

        List<Client> clientListExpect = new ArrayList<>();

        clientListExpect.add(new Client());
        clientListExpect.add(new Client());
        clientListExpect.add(new Client());

        when(mockClientRepository.findAllByUser_Id(1L)).thenReturn(clientListExpect);

        List<Client> clientListActual = clientService.getAllMyClients();


        Assertions.assertEquals(clientListExpect.size(), clientListActual.size());



    }

    @Test
    void testDeleteClient() {
        User user = new User();
        user.setId(1L);
        when(mockLoggedUserService.getUser()).thenReturn(user);

        Long clientIdToDelete = 1L;
        Optional<Client> clientToDelete = Optional.of(new Client());

        when(mockClientRepository.findByIdAndUser_id(clientIdToDelete, 1L)).thenReturn(clientToDelete);

        clientService.deleteClient(clientIdToDelete);

        Assertions.assertEquals(0, mockClientRepository.count());


    }


}
