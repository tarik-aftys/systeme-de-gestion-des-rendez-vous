package com.appointmentapp.service;

import com.appointmentapp.domain.Creneau;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.Service;
import com.appointmentapp.domain.enums.StatutCreneau;
import com.appointmentapp.repository.CreneauRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CreneauService {
    private final CreneauRepository creneauRepository;

    public Creneau creerCreneau(Creneau creneau) {
        if (creneau.getDateDebut() == null || creneau.getDateFin() == null) {
            throw new IllegalArgumentException("Les dates du créneau sont obligatoires");
        }
        if (!creneau.getDateFin().isAfter(creneau.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être après la date de début");
        }
        creneau.setStatut(StatutCreneau.DISPONIBLE);
        return creneauRepository.save(creneau);
    }

    public List<Creneau> obtenirCreneauxDuPrestataire(Prestataire prestataire) {
        return creneauRepository.findByPrestataire(prestataire);
    }

    public List<Creneau> obtenirCreneauxParService(Service service) {
        return creneauRepository.findByService(service);
    }

    public List<Creneau> obtenirCreneauxDisponibles(Prestataire prestataire) {
        return creneauRepository.findByPrestataireAndStatut(prestataire, StatutCreneau.DISPONIBLE);
    }

    public boolean verifierDisponibilite(Creneau creneau) {
        return creneau != null && creneau.verifierDisponibilite();
    }

    public void reserver(Creneau creneau) {
        creneau.reserver();
        creneauRepository.save(creneau);
    }

    public void liberer(Creneau creneau) {
        creneau.liberer();
        creneauRepository.save(creneau);
    }

    public List<Creneau> rechercherDisponibilites(Prestataire prestataire, LocalDateTime debut, LocalDateTime fin) {
        return creneauRepository.findByPrestataireAndDateDebutBetween(prestataire, debut, fin);
    }
}
