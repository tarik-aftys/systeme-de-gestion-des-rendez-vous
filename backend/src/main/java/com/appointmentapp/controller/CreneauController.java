package com.appointmentapp.controller;

import com.appointmentapp.dto.CreneauCreateDTO;
import com.appointmentapp.domain.Creneau;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.Service;
import com.appointmentapp.repository.CreneauRepository;
import com.appointmentapp.repository.ServiceRepository;
import com.appointmentapp.service.CreneauService;
import com.appointmentapp.repository.PrestataireRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/creneaux")
@RequiredArgsConstructor
public class CreneauController {
    private final CreneauService creneauService;
    private final PrestataireRepository prestataireRepository;
    private final ServiceRepository serviceRepository;
    private final CreneauRepository creneauRepository;

    @GetMapping
    public ResponseEntity<List<Creneau>> getAll() {
        return ResponseEntity.ok(creneauRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Creneau> getById(@PathVariable Long id) {
        return creneauRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Creneau> create(@Valid @RequestBody CreneauCreateDTO dto) {
        return prestataireRepository.findById(dto.getPrestataireId())
                .flatMap(prestataire -> serviceRepository.findById(dto.getServiceId())
                        .map(service -> {
                            Creneau creneau = new Creneau();
                            creneau.setDateDebut(dto.getDateDebut());
                            creneau.setDateFin(dto.getDateFin());
                            creneau.setPrestataire(prestataire);
                            creneau.setService(service);
                            Creneau saved = creneauService.creerCreneau(creneau);
                            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
                        }))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prestataire/{prestataireId}")
    public ResponseEntity<List<Creneau>> getByPrestataire(@PathVariable Long prestataireId) {
        return prestataireRepository.findById(prestataireId)
                .map(prest -> ResponseEntity.ok(creneauService.obtenirCreneauxDuPrestataire(prest)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return creneauRepository.findById(id)
                .map(creneau -> {
                    creneauRepository.delete(creneau);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
