package com.appointmentapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvisCreateDTO {
    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer note;

    @NotBlank(message = "Comment cannot be blank")
    private String commentaire;

    @NotNull(message = "Client ID cannot be null")
    private Long clientId;

    @NotNull(message = "Appointment ID cannot be null")
    private Long rendezVousId;

    @NotNull(message = "Service ID cannot be null")
    private Long serviceId;
}