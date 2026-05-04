package com.appointmentapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * DTO for Avis entity
 * Represents customer reviews and ratings for services
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvisDTO {
    
    private Long id;
    
    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer note;
    
    @NotBlank(message = "Comment cannot be blank")
    private String commentaire;
    
    private LocalDateTime date;
    
    private String statut;
    
    // Client info
    private Long clientId;
    private String clientNom;
    
    // Appointment info
    private Long rendezVousId;
    
    // Service info
    private Long serviceId;
    private String serviceName;
}
