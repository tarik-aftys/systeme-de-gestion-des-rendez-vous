package com.appointmentapp.service;

import com.appointmentapp.domain.Client;
import com.appointmentapp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Client management
 * Handles business logic related to clients
 */
@Service
@RequiredArgsConstructor
public class ClientService {
    
    private final ClientRepository clientRepository;
    
    /**
     * Find all clients (excluding deleted ones)
     */
    public List<Client> findAll() {
        return clientRepository.findByEstSupprimeIsFalse();
    }
    
    /**
     * Find a client by ID
     */
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id)
                .filter(client -> !client.getEstSupprime());
    }
    
    /**
     * Find a client by email
     */
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .filter(client -> !client.getEstSupprime());
    }
    
    /**
     * Save or update a client
     */
    public Client save(Client client) {
        return clientRepository.save(client);
    }
    
    /**
     * Delete a client (soft delete by marking as supprime)
     */
    public void delete(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            client.setEstSupprime(true);
            clientRepository.save(client);
        });
    }
    
    /**
     * Check if email already exists
     */
    public boolean emailExists(String email) {
        return clientRepository.findByEmail(email).isPresent();
    }
}
