package com.appointmentapp.controller;

import com.appointmentapp.domain.enums.RoleUser;
import com.appointmentapp.dto.ClientCreateDTO;
import com.appointmentapp.dto.ClientDTO;
import com.appointmentapp.domain.Client;
import com.appointmentapp.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import org.hibernate.Hibernate;

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
    private final PasswordEncoder passwordEncoder;
    
    /**
     * GET: Retrieve all clients
     * @return List of all clients
     */
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> dtos = clientService.findAllDTOs();
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve a specific client by ID
     * @param id Client ID
     * @return Client details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return clientService.findDTOById(id)
                .map(ResponseEntity::ok)
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

        // On crypte le mot de passe ici
        client.setPassword(passwordEncoder.encode(createDTO.getPassword()));

        client.setTelephone(createDTO.getTelephone());
        client.setAdresse(createDTO.getAdresse());
        client.setDateNaissance(createDTO.getDateNaissance());
        client.setRole(RoleUser.CLIENT);
        client.setStatut("ACTIF");
        client.setEstSupprime(false);

        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.findDTOById(savedClient.getId()).orElseThrow());
    }
    
    /**
     * PUT: Update an existing client
     * @param id Client ID
     * @param createDTO Updated client data
     * @return Updated client or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientCreateDTO createDTO) {

        return clientService.findById(id)
                .map(client -> {
                    // ✅ Vérification unicité email (seulement si l'email change)
                    if (!client.getEmail().equalsIgnoreCase(createDTO.getEmail())) {
                        if (clientService.emailExists(createDTO.getEmail())) {
                            return ResponseEntity
                                    .status(HttpStatus.CONFLICT)
                                    .body("Cet email est déjà utilisé par un autre compte.");
                        }
                        client.setEmail(createDTO.getEmail()); // ✅ Ligne manquante
                    }

                    client.setNom(createDTO.getNom());
                    client.setTelephone(createDTO.getTelephone());
                    client.setAdresse(createDTO.getAdresse());
                    client.setDateNaissance(createDTO.getDateNaissance());
                    // ✅ On ne touche PAS au password ("NOPASSWORD" envoyé par le frontend est ignoré)

                    Client updated = clientService.save(client);
                    return ResponseEntity.ok(
                            clientService.findDTOById(updated.getId()).orElseThrow()
                    );
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
        // keep for backwards compatibility but avoid accessing lazy collections here
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
        int total = 0;
        try {
            if (Hibernate.isInitialized(client.getRendezVous())) {
                total = client.getRendezVous() == null ? 0 : client.getRendezVous().size();
            }
        } catch (NoClassDefFoundError e) {
            // Hibernate not on classpath in some test contexts — fall back safely
            total = 0;
        }
        dto.setTotalRendezVous(total);
        return dto;
    }
}
