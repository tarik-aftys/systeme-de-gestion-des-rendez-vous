package com.appointmentapp.dto;

import com.appointmentapp.domain.enums.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for User entity - Response
 * Used to transfer user data from server to client
 * (Does NOT include sensitive fields like password)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Name cannot be blank")
    private String nom;
    
    private String telephone;
    
    @NotNull(message = "Role cannot be null")
    private RoleUser role;
    
    private String statut;
    
    private Boolean estSupprime;
}
