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
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String nom;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 8, max = 20, message = "Phone number must be between 8 and 20 characters")
    private String telephone;

    @NotBlank(message = "Secteur cannot be blank")
    private String secteur;

    @NotBlank(message = "Localisation cannot be blank")
    private String localisation;

    @NotBlank(message = "Horaires cannot be blank")
    private String horaires;
}
