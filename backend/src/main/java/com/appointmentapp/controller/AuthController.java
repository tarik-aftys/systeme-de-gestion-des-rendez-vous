package com.appointmentapp.controller;

import com.appointmentapp.dto.LoginRequest;
import com.appointmentapp.dto.LoginResponse;
import com.appointmentapp.repository.ClientRepository;
import com.appointmentapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // 1. Authentification
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // 2. Génération du Token (La ligne qui manquait !)
        String token = tokenProvider.generateToken(loginRequest.getUsername());

        // 3. Récupération du nom pour l'affichage
        String nomAafficher = "Administrateur";

        if (!"admin".equals(loginRequest.getUsername())) {
            nomAafficher = clientRepository.findByEmail(loginRequest.getUsername())
                    .map(c -> c.getNom())
                    .orElse("Client");
        }

        // 4. Envoi de la réponse complète
        return ResponseEntity.ok(new LoginResponse(token, "Bearer", loginRequest.getUsername(), nomAafficher));
    }
}
