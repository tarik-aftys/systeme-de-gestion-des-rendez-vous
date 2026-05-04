package com.appointmentapp.controller;

import com.appointmentapp.dto.RendezVousCreateDTO;
import com.appointmentapp.dto.RendezVousDTO;
import com.appointmentapp.domain.RendezVous;
import com.appointmentapp.domain.enums.StatutRDV;
import com.appointmentapp.service.RendezVousService;
import com.appointmentapp.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Appointment (RendezVous) management
 * Handles HTTP requests related to appointment bookings
 * Base URL: /api/rendez-vous
 */
@RestController
@RequestMapping("/api/rendez-vous")
@RequiredArgsConstructor
public class RendezVousController {
    
    private final RendezVousService rendezVousService;
    private final ClientService clientService;
    
    /**
     * GET: Retrieve all appointments
     * @return List of all appointments
     */
    @GetMapping
    public ResponseEntity<List<RendezVousDTO>> getAllAppointments() {
        List<RendezVous> appointments = rendezVousService.obtenirTousLesRendezVous();
        List<RendezVousDTO> dtos = appointments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve a specific appointment by ID
     * @param id Appointment ID
     * @return Appointment details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<RendezVousDTO> getAppointmentById(@PathVariable Long id) {
        return rendezVousService.findById(id)
                .map(rdv -> ResponseEntity.ok(convertToDTO(rdv)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET: Retrieve all appointments for a specific client
     * @param clientId Client ID
     * @return List of client's appointments
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<RendezVousDTO>> getAppointmentsByClient(@PathVariable Long clientId) {
        return clientService.findById(clientId)
                .map(client -> {
                    List<RendezVous> appointments = rendezVousService.obtenirRendezVousDuClient(client);
                    List<RendezVousDTO> dtos = appointments.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(dtos);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * POST: Create a new appointment
     * @param createDTO Appointment creation data
     * @return Created appointment with 201 status
     */
    @PostMapping
    public ResponseEntity<RendezVousDTO> createAppointment(@Valid @RequestBody RendezVousCreateDTO createDTO) {
        try {
            RendezVous appointment = rendezVousService.creerRendezVous(
                    clientService.findById(createDTO.getClientId()).orElseThrow(),
                    null, // Provider - to be fetched from repository if needed
                    null, // Service - to be fetched from repository if needed
                    null  // Creneau - to be fetched from repository if needed
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(appointment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * PUT: Cancel an appointment
     * @param id Appointment ID
     * @return Updated appointment or 404 if not found
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<RendezVousDTO> cancelAppointment(@PathVariable Long id) {
        return rendezVousService.findById(id)
                .map(appointment -> {
                    appointment.setStatut(StatutRDV.ANNULE);
                    RendezVous updated = rendezVousService.save(appointment);
                    return ResponseEntity.ok(convertToDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * DELETE: Delete an appointment
     * @param id Appointment ID
     * @return 204 No Content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        return rendezVousService.findById(id)
                .map(appointment -> {
                    rendezVousService.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Helper method: Convert RendezVous entity to DTO
     */
    private RendezVousDTO convertToDTO(RendezVous rdv) {
        RendezVousDTO dto = new RendezVousDTO();
        dto.setId(rdv.getId());
        dto.setDate(rdv.getDate());
        dto.setStatut(rdv.getStatut());
        
        if (rdv.getClient() != null) {
            dto.setClientId(rdv.getClient().getId());
            dto.setClientNom(rdv.getClient().getNom());
            dto.setClientEmail(rdv.getClient().getEmail());
        }
        
        if (rdv.getPrestataire() != null) {
            dto.setPrestataireId(rdv.getPrestataire().getId());
            dto.setPrestataireName(rdv.getPrestataire().getNom());
        }
        
        if (rdv.getService() != null) {
            dto.setServiceId(rdv.getService().getId());
            dto.setServiceName(rdv.getService().getNom());
            dto.setServicePrix(rdv.getService().getPrix());
        }
        
        if (rdv.getPaiement() != null) {
            dto.setPaiementId(rdv.getPaiement().getId());
            dto.setPaiementStatut(rdv.getPaiement().getStatut().toString());
        }
        
        return dto;
    }
}
