package com.appointmentapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreneauCreateDTO {
    @NotNull(message = "Start date cannot be null")
    private LocalDateTime dateDebut;

    @NotNull(message = "End date cannot be null")
    private LocalDateTime dateFin;

    @NotNull(message = "Provider ID cannot be null")
    private Long prestataireId;

    @NotNull(message = "Service ID cannot be null")
    private Long serviceId;
}