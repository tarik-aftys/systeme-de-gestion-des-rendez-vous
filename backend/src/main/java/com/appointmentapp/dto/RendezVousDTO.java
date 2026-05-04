package com.appointmentapp.dto;

import com.appointmentapp.domain.enums.StatutRDV;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for RendezVous entity - Response
 * Used to transfer appointment data from server to client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousDTO {
    
    private Long id;
    
    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;
    
    @NotNull(message = "Status cannot be null")
    private StatutRDV statut;
    
    // Client info
    private Long clientId;
    private String clientNom;
    private String clientEmail;
    
    // Provider info
    private Long prestataireId;
    private String prestataireName;
    
    // Service info
    private Long serviceId;
    private String serviceName;
    private Double servicePrix;
    
    // Payment info
    private Long paiementId;
    private String paiementStatut;
}
