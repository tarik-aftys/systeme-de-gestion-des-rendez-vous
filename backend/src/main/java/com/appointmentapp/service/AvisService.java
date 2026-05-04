package com.appointmentapp.service;

import com.appointmentapp.domain.Avis;
import com.appointmentapp.domain.Client;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.RendezVous;
import com.appointmentapp.repository.AvisRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class AvisService {
    private final AvisRepository avisRepository;

    public Avis ajouterAvis(Client client, Prestataire prestataire, RendezVous rendezVous, Avis avis) {
        if (rendezVous == null || rendezVous.getStatut() == null || rendezVous.getStatut() != com.appointmentapp.domain.enums.StatutRDV.TERMINE) {
            throw new IllegalStateException("Un avis ne peut être ajouté que pour un rendez-vous terminé");
        }

        avis.setClient(client);
        avis.setPrestataire(prestataire);
        avis.setRendezVous(rendezVous);
        avis.setDate(LocalDateTime.now());
        return avisRepository.save(avis);
    }

    public List<Avis> obtenirAvisDuPrestataire(Prestataire prestataire) {
        return avisRepository.findByPrestataire(prestataire);
    }

    public List<Avis> obtenirAvisDuRendezVous(RendezVous rendezVous) {
        return avisRepository.findByRendezVous(rendezVous);
    }

    public Avis modifierAvis(Avis avis) {
        return avisRepository.save(avis);
    }

    public void supprimerAvis(Avis avis) {
        avisRepository.delete(avis);
    }
}
