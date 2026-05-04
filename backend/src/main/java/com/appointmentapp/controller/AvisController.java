package com.appointmentapp.controller;

import com.appointmentapp.dto.AvisDTO;
import com.appointmentapp.domain.Avis;
import com.appointmentapp.service.AvisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Review (Avis) management
 * Handles HTTP requests related to customer reviews and ratings
 * Base URL: /api/avis
 */
@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {
    
    private final AvisService avisService;
    
    /**
     * GET: Retrieve all reviews
     * @return List of all reviews
     */
    @GetMapping
    public ResponseEntity<List<AvisDTO>> getAllReviews() {
        List<Avis> reviews = avisService.obtenirTousLesAvis();
        List<AvisDTO> dtos = reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve a specific review by ID
     * @param id Review ID
     * @return Review details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvisDTO> getReviewById(@PathVariable Long id) {
        return avisService.findById(id)
                .map(avis -> ResponseEntity.ok(convertToDTO(avis)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET: Retrieve all reviews for a specific appointment
     * @param rendezVousId Appointment ID
     * @return Reviews for the appointment or 404 if appointment not found
     */
    @GetMapping("/rendez-vous/{rendezVousId}")
    public ResponseEntity<List<AvisDTO>> getReviewsByAppointment(@PathVariable Long rendezVousId) {
        List<Avis> reviews = avisService.obtenirAvisParRendezVous(rendezVousId);
        List<AvisDTO> dtos = reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve all reviews for a specific service
     * @param serviceId Service ID
     * @return Reviews for the service
     */
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<AvisDTO>> getReviewsByService(@PathVariable Long serviceId) {
        List<Avis> reviews = avisService.obtenirAvisParService(serviceId);
        List<AvisDTO> dtos = reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Get average rating for a service
     * @param serviceId Service ID
     * @return Average rating (1-5)
     */
    @GetMapping("/service/{serviceId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long serviceId) {
        Double average = avisService.obtenirNoyenneLesNotes(serviceId);
        return ResponseEntity.ok(average);
    }
    
    /**
     * POST: Create a new review
     * @param createDTO Review creation data
     * @return Created review with 201 status
     */
    @PostMapping
    public ResponseEntity<AvisDTO> createReview(@Valid @RequestBody AvisDTO createDTO) {
        try {
            Avis avis = new Avis();
            avis.setNote(createDTO.getNote());
            avis.setCommentaire(createDTO.getCommentaire());
            avis.setDate(java.time.LocalDateTime.now());
            avis.setStatut("PUBLIE");
            
            Avis savedAvis = avisService.save(avis);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedAvis));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * PUT: Update an existing review
     * @param id Review ID
     * @param updateDTO Updated review data
     * @return Updated review or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvisDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody AvisDTO updateDTO) {
        
        return avisService.findById(id)
                .map(avis -> {
                    avis.setNote(updateDTO.getNote());
                    avis.setCommentaire(updateDTO.getCommentaire());
                    Avis updated = avisService.save(avis);
                    return ResponseEntity.ok(convertToDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * DELETE: Delete a review
     * @param id Review ID
     * @return 204 No Content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        return avisService.findById(id)
                .map(avis -> {
                    avisService.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Helper method: Convert Avis entity to DTO
     */
    private AvisDTO convertToDTO(Avis avis) {
        AvisDTO dto = new AvisDTO();
        dto.setId(avis.getId());
        dto.setNote(avis.getNote());
        dto.setCommentaire(avis.getCommentaire());
        dto.setDate(avis.getDate());
        dto.setStatut(avis.getStatut());
        
        if (avis.getClient() != null) {
            dto.setClientId(avis.getClient().getId());
            dto.setClientNom(avis.getClient().getNom());
        }
        
        if (avis.getRendezVous() != null) {
            dto.setRendezVousId(avis.getRendezVous().getId());
            if (avis.getRendezVous().getService() != null) {
                dto.setServiceId(avis.getRendezVous().getService().getId());
                dto.setServiceName(avis.getRendezVous().getService().getNom());
            }
        }
        
        return dto;
    }
}
