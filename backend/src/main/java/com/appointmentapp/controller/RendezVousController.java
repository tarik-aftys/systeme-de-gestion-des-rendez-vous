package com.appointmentapp.controller;

import com.appointmentapp.dto.RendezVousCreateDTO;
import com.appointmentapp.dto.RendezVousDTO;
import com.appointmentapp.domain.RendezVous;
import com.appointmentapp.domain.Creneau;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.Service;
import com.appointmentapp.domain.enums.StatutRDV;
import com.appointmentapp.repository.CreneauRepository;
import com.appointmentapp.repository.PrestataireRepository;
import com.appointmentapp.repository.ServiceRepository;
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
    private final PrestataireRepository prestataireRepository;
    private final ServiceRepository serviceRepository;
    private final CreneauRepository creneauRepository;
    
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
        return clientService.findById(createDTO.getClientId())
                .flatMap(client -> prestataireRepository.findById(createDTO.getPrestataireId())
                        .flatMap(prestataire -> serviceRepository.findById(createDTO.getServiceId())
                                .flatMap(service -> creneauRepository.findById(createDTO.getCreneauId())
                                        .map(creneau -> new Object[]{client, prestataire, service, creneau}))))
                .map(data -> {
                    com.appointmentapp.domain.Client client = (com.appointmentapp.domain.Client) data[0];
                    Prestataire prestataire = (Prestataire) data[1];
                    Service service = (Service) data[2];
                    Creneau creneau = (Creneau) data[3];

                    if (creneau.getPrestataire() != null && !creneau.getPrestataire().getId().equals(prestataire.getId())) {
                        return ResponseEntity.badRequest().<RendezVousDTO>build();
                    }
                    if (creneau.getService() != null && !creneau.getService().getId().equals(service.getId())) {
                        return ResponseEntity.badRequest().<RendezVousDTO>build();
                    }

                    RendezVous appointment = rendezVousService.creerRendezVous(
                            client,
                            prestataire,
                            service,
                            creneau,
                            createDTO.getDate()
                    );
                    return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(appointment));
                })
                .orElse(ResponseEntity.notFound().build());
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
