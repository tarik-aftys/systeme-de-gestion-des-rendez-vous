package com.appointmentapp.service;

import com.appointmentapp.domain.Client;
import com.appointmentapp.domain.Creneau;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.RendezVous;
import com.appointmentapp.domain.enums.StatutRDV;
import com.appointmentapp.repository.RendezVousRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    // On garde uniquement la création de base sans Paiement ni CreneauService
    public RendezVous creerRendezVous(Client client, Prestataire prestataire, com.appointmentapp.domain.Service service, Creneau creneau, LocalDateTime date) {
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDate(date != null ? date : LocalDateTime.now());
        rendezVous.setStatut(StatutRDV.EN_ATTENTE);
        rendezVous.setClient(client);
        rendezVous.setPrestataire(prestataire);
        rendezVous.setService(service);
        rendezVous.setCreneau(creneau);

        // On ne crée pas de paiement ici pour l'instant
        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> obtenirTousLesRendezVous() {
        return rendezVousRepository.findAll();
    }

    public List<RendezVous> obtenirRendezVousDuClient(Client client) {
        return rendezVousRepository.findByClient(client);
    }

    public Optional<RendezVous> findById(Long id) {
        return rendezVousRepository.findById(id);
    }

    public RendezVous save(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    public void delete(Long id) {
        rendezVousRepository.deleteById(id);
    }
}