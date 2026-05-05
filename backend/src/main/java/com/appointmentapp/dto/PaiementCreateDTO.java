package com.appointmentapp.dto;

import com.appointmentapp.domain.enums.StatutPaiement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementCreateDTO {
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double montant;

    @NotNull(message = "Payment date cannot be null")
    private LocalDateTime datePaiement;

    private StatutPaiement statut;

    private String methodePaiement;

    @NotNull(message = "Appointment ID cannot be null")
    private Long rendezVousId;
}