package com.appointmentapp.dto;

import com.appointmentapp.domain.enums.RoleUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for User entity - Create Request
 * Used when client sends data to create a new user
 * (INCLUDES password for authentication setup)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String nom;
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    private String telephone;
    
    @NotNull(message = "Role cannot be null")
    private RoleUser role;
    
    private String statut = "ACTIF";
}
