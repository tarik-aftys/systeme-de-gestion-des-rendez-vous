package com.appointmentapp.controller;

import com.appointmentapp.dto.PrestataireCreateDTO;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.repository.PrestataireRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestataires")
@RequiredArgsConstructor
public class PrestataireController {
    private final PrestataireRepository prestataireRepository;

    @GetMapping
    public ResponseEntity<List<Prestataire>> getAll() {
        return ResponseEntity.ok(prestataireRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestataire> getById(@PathVariable Long id) {
        return prestataireRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Prestataire> create(@Valid @RequestBody PrestataireCreateDTO dto) {
        Prestataire p = new Prestataire();
        p.setEmail(dto.getEmail());
        p.setNom(dto.getNom());
        p.setPassword(dto.getPassword());
        p.setTelephone(dto.getTelephone());
        p.setSecteur(dto.getSecteur());
        p.setLocalisation(dto.getLocalisation());
        p.setHoraires(dto.getHoraires());
        p.setEstSupprime(false);
        Prestataire saved = prestataireRepository.save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestataire> update(@PathVariable Long id, @Valid @RequestBody PrestataireCreateDTO dto) {
        return prestataireRepository.findById(id).map(p -> {
            p.setNom(dto.getNom());
            p.setTelephone(dto.getTelephone());
            p.setSecteur(dto.getSecteur());
            p.setLocalisation(dto.getLocalisation());
            p.setHoraires(dto.getHoraires());
            return ResponseEntity.ok(prestataireRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return prestataireRepository.findById(id).map(p -> {
            p.setEstSupprime(true);
            prestataireRepository.save(p);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
