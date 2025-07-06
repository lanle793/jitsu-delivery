package com.jitsu.delivery.api.service;

import com.jitsu.delivery.api.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean clientExists(Long clientId) {
        return clientRepository.existsById(clientId);
    }
}
