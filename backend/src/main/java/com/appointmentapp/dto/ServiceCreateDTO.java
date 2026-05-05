package com.appointmentapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCreateDTO {
    @NotBlank(message = "Service name cannot be blank")
    private String nom;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double prix;

    @NotNull(message = "Provider ID cannot be null")
    private Long prestataireId;

    @NotNull(message = "Duration cannot be null")
    @Positive(message = "Duration must be positive")
    private Integer duree;

    private Boolean estDisponible = true;
}