package com.appointmentapp.controller;

import com.appointmentapp.domain.User;
import com.appointmentapp.dto.LoginRequest;
import com.appointmentapp.dto.LoginResponse;
import com.appointmentapp.repository.UserRepository;
import com.appointmentapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = tokenProvider.generateToken(loginRequest.getUsername());

        User user = userRepository.findByEmail(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getEstSupprime()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Ce compte a été supprimé. Contactez l'administrateur.");
        }
        return ResponseEntity.ok(new LoginResponse(
                token,
                "Bearer",
                user.getEmail(),
                user.getNom(),
                user.getId() //
        ));
    }
}