package com.appointmentapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * DTO for Service entity
 * Represents an available service offered by the appointment system
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    
    private Long id;
    
    @NotBlank(message = "Service name cannot be blank")
    private String nom;
    
    private String description;
    
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double prix;
    
    private Integer duree;
    
    private Boolean estDisponible = true;
}
