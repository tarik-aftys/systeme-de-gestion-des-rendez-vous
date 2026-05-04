package com.appointmentapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * DTO for Client entity - Response
 * Extends UserDTO to include client-specific fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientDTO extends UserDTO {
    
    @NotBlank(message = "Address cannot be blank")
    private String adresse;
    
    private LocalDate dateNaissance;
    
    private Integer totalRendezVous;
}
