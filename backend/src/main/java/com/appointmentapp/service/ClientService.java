package com.appointmentapp.service;

import com.appointmentapp.domain.Client;
import com.appointmentapp.dto.ClientDTO;
import com.appointmentapp.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findByEstSupprimeIsFalse();
    }
    
    /**
     * Find a client by ID
     */
    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id)
                .filter(client -> !client.getEstSupprime());
    }
    
    /**
     * Find a client by email
     */
    @Transactional(readOnly = true)
    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .filter(client -> !client.getEstSupprime());
    }
    
    /**
     * Save or update a client and initialize relationships
     */
    @Transactional
    public Client save(Client client) {
        Client saved = clientRepository.save(client);
        // Force initialization of lazy-loaded collections
        saved.getRendezVous().size();
        saved.getAvis().size();
        return saved;
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

    @Transactional(readOnly = true)
    public java.util.List<ClientDTO> findAllDTOs() {
        return clientRepository.findByEstSupprimeIsFalse().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public java.util.Optional<ClientDTO> findDTOById(Long id) {
        return clientRepository.findById(id)
                .filter(client -> !client.getEstSupprime())
                .map(this::toDTO);
    }

    private ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setEmail(client.getEmail());
        dto.setNom(client.getNom());
        dto.setTelephone(client.getTelephone());
        dto.setRole(client.getRole());
        dto.setStatut(client.getStatut());
        dto.setEstSupprime(client.getEstSupprime());
        dto.setAdresse(client.getAdresse());
        dto.setDateNaissance(client.getDateNaissance());
        dto.setTotalRendezVous(client.getRendezVous() == null ? 0 : client.getRendezVous().size());
        return dto;
    }
}
