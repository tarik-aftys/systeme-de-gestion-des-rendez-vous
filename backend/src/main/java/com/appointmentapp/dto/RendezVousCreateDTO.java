package com.appointmentapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for RendezVous entity - Create Request
 * Used when client sends data to book a new appointment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVousCreateDTO {
    
    @NotNull(message = "Appointment date cannot be null")
    private LocalDateTime date;
    
    @NotNull(message = "Client ID cannot be null")
    private Long clientId;
    
    @NotNull(message = "Provider ID cannot be null")
    private Long prestataireId;
    
    @NotNull(message = "Service ID cannot be null")
    private Long serviceId;
    
    @NotNull(message = "Time slot ID cannot be null")
    private Long creneauId;
}
