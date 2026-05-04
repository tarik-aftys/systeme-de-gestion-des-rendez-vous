package com.appointmentapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestataireCreateDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 2)
    private String nom;

    @NotBlank
    private String password;

    private String telephone;

    @NotBlank
    private String secteur;

    @NotBlank
    private String localisation;

    @NotBlank
    private String horaires;
}
