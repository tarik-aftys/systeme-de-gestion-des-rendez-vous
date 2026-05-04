package com.appointmentapp.controller;

import com.appointmentapp.dto.PaiementDTO;
import com.appointmentapp.domain.Paiement;
import com.appointmentapp.domain.enums.StatutPaiement;
import com.appointmentapp.service.PaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Payment management
 * Handles HTTP requests related to payment processing
 * Base URL: /api/paiements
 */
@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {
    
    private final PaiementService paiementService;
    
    /**
     * GET: Retrieve all payments
     * @return List of all payments
     */
    @GetMapping
    public ResponseEntity<List<PaiementDTO>> getAllPayments() {
        List<Paiement> payments = paiementService.findAll();
        List<PaiementDTO> dtos = payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve a specific payment by ID
     * @param id Payment ID
     * @return Payment details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> getPaymentById(@PathVariable Long id) {
        return paiementService.findById(id)
                .map(payment -> ResponseEntity.ok(convertToDTO(payment)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET: Retrieve all pending payments
     * @return List of pending payments
     */
    @GetMapping("/status/pending")
    public ResponseEntity<List<PaiementDTO>> getPendingPayments() {
        List<Paiement> payments = paiementService.findPendingPayments();
        List<PaiementDTO> dtos = payments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    /**
     * GET: Retrieve payments by status
     * @param statut Payment status (EN_ATTENTE, PAYE, REMBOURSE)
     * @return List of payments with specified status
     */
    @GetMapping("/status/{statut}")
    public ResponseEntity<List<PaiementDTO>> getPaymentsByStatus(@PathVariable String statut) {
        try {
            StatutPaiement status = StatutPaiement.valueOf(statut.toUpperCase());
            List<Paiement> payments = paiementService.findByStatut(status);
            List<PaiementDTO> dtos = payments.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * POST: Create a new payment
     * @param createDTO Payment creation data
     * @return Created payment with 201 status
     */
    @PostMapping
    public ResponseEntity<PaiementDTO> createPayment(@Valid @RequestBody PaiementDTO createDTO) {
        try {
            Paiement paiement = new Paiement();
            paiement.setMontant(createDTO.getMontant());
            paiement.setDatePaiement(createDTO.getDatePaiement());
            paiement.setStatut(createDTO.getStatut() != null ? createDTO.getStatut() : StatutPaiement.EN_ATTENTE);
            paiement.setMethodePaiement(createDTO.getMethodePaiement());
            
            Paiement savedPaiement = paiementService.save(paiement);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPaiement));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * PUT: Update an existing payment
     * @param id Payment ID
     * @param updateDTO Updated payment data
     * @return Updated payment or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaiementDTO> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaiementDTO updateDTO) {
        
        return paiementService.findById(id)
                .map(paiement -> {
                    paiement.setMontant(updateDTO.getMontant());
                    paiement.setMethodePaiement(updateDTO.getMethodePaiement());
                    Paiement updated = paiementService.save(paiement);
                    return ResponseEntity.ok(convertToDTO(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * PATCH: Update payment status
     * @param id Payment ID
     * @param newStatus New payment status
     * @return Updated payment or 404/400 if error
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PaiementDTO> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {
        
        try {
            StatutPaiement status = StatutPaiement.valueOf(newStatus.toUpperCase());
            Paiement updated = paiementService.updateStatus(id, status);
            return ResponseEntity.ok(convertToDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * DELETE: Delete a payment
     * @param id Payment ID
     * @return 204 No Content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        return paiementService.findById(id)
                .map(payment -> {
                    paiementService.delete(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Helper method: Convert Paiement entity to DTO
     */
    private PaiementDTO convertToDTO(Paiement paiement) {
        PaiementDTO dto = new PaiementDTO();
        dto.setId(paiement.getId());
        dto.setMontant(paiement.getMontant());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setStatut(paiement.getStatut());
        dto.setMethodePaiement(paiement.getMethodePaiement());
        return dto;
    }
}
