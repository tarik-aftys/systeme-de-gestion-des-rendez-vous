package com.appointmentapp.dto;

import com.appointmentapp.domain.enums.RoleUser;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for Client entity - Create Request
 * Used when client sends data to create a new client account
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateDTO {
    
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
    
    @NotBlank(message = "Address cannot be blank")
    private String adresse;
    
    @NotNull(message = "Date of birth cannot be null")
    @PastOrPresent(message = "Date of birth must be in the past")
    private LocalDate dateNaissance;
}
