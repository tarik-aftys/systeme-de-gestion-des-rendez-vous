package com.appointmentapp.controller;

import com.appointmentapp.dto.UserCreateDTO;
import com.appointmentapp.dto.UserDTO;
import com.appointmentapp.domain.User;
import com.appointmentapp.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = users.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> ResponseEntity.ok(toDTO(user))).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO createDTO) {
        User user = new User();
        user.setEmail(createDTO.getEmail());
        user.setNom(createDTO.getNom());
        user.setPassword(createDTO.getPassword());
        user.setTelephone(createDTO.getTelephone());
        user.setRole(createDTO.getRole());
        user.setStatut(createDTO.getStatut());
        user.setEstSupprime(false);
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDTO updateDTO) {
        return userRepository.findById(id).map(user -> {
            user.setNom(updateDTO.getNom());
            user.setTelephone(updateDTO.getTelephone());
            user.setStatut(updateDTO.getStatut());
            User updated = userRepository.save(user);
            return ResponseEntity.ok(toDTO(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setEstSupprime(true);
            userRepository.save(user);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNom(user.getNom());
        dto.setTelephone(user.getTelephone());
        dto.setRole(user.getRole());
        dto.setStatut(user.getStatut());
        dto.setEstSupprime(user.getEstSupprime());
        return dto;
    }
}
