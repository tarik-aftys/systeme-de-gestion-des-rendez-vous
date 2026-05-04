package com.appointmentapp.dto;

import com.appointmentapp.domain.enums.StatutPaiement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Paiement entity
 * Represents payment information for appointments
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {
    
    private Long id;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double montant;
    
    @NotNull(message = "Payment date cannot be null")
    private LocalDateTime datePaiement;
    
    @NotNull(message = "Payment status cannot be null")
    private StatutPaiement statut;
    
    private String methodePaiement;
    
    private Long rendezVousId;
}
