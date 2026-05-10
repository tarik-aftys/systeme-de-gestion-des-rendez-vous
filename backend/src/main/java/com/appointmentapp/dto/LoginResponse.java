package com.appointmentapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private String username; // Email
    private String nom;      // Nom complet récupéré en BDD
    private Long userId;
    private String role;
}