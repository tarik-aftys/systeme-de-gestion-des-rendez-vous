package com.appointmentapp.controller;

import com.appointmentapp.dto.ClientCreateDTO;
import com.appointmentapp.dto.ClientDTO;
import com.appointmentapp.domain.Client;
import com.appointmentapp.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Client management
 * Handles HTTP requests related to client operations
 * Base URL: /api/clients
 */
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    
    private final ClientService clientService;
    
    /**
     * GET: Retrieve all clients
     * @return List of all clients
     */
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientService.findAll();
        List<ClientDTO> dtos = clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve a specific client by ID
     * @param id Client ID
     * @return Client details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return clientService.findById(id)
                .map(client -> ResponseEntity.ok(convertToDTO(client)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST: Create a new client account
     * @param createDTO Client creation data
     * @return Created client with 201 status
     */
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClientCreateDTO createDTO) {
        Client client = new Client();
        client.setEmail(createDTO.getEmail());
        client.setNom(createDTO.getNom());
        client.setPassword(createDTO.getPassword());
        client.setTelephone(createDTO.getTelephone());
        client.setAdresse(createDTO.getAdresse());
        client.setDateNaissance(createDTO.getDateNaissance());
        client.setStatut("ACTIF");
        client.setEstSupprime(false);
        
        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedClient));
    }
    
    /**
     * PUT: Update an existing client
     * @param id Client ID
     * @param createDTO Updated client data
     * @return Updated client or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientCreateDTO createDTO) {
        
        return clientService.findById(id)
                .map(client -> {
                    client.setNom(createDTO.getNom());
                    client.setTelephone(createDTO.getTelephone());
                    client.setAdresse(createDTO.getAdresse());
                    client.setDateNaissance(createDTO.getDateNaissance());
                    Client updated = clientService.save(client);
                    return ResponseEntity.ok(convertToDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * DELETE: Delete a client (soft delete)
     * @param id Client ID
     * @return 204 No Content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return clientService.findById(id)
                .map(client -> {
                    client.setEstSupprime(true);
                    clientService.save(client);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Helper method: Convert Client entity to DTO
     */
    private ClientDTO convertToDTO(Client client) {
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
        dto.setTotalRendezVous(client.getRendezVous().size());
        return dto;
    }
}
