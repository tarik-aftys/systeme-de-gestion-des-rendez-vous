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
        if (rendezVous == null || rendezVous.getStatut() == null) {
            throw new IllegalStateException("Un avis ne peut être ajouté que pour un rendez-vous terminé");
        }

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
    
    public List<Avis> obtenirTousLesAvis() {
        return avisRepository.findAll();
    }
    
    public java.util.Optional<Avis> findById(Long id) {
        return avisRepository.findById(id);
    }
    
    public Avis save(Avis avis) {
        return avisRepository.save(avis);
    }
    
    public void delete(Long id) {
        avisRepository.deleteById(id);
    }
    
    public List<Avis> obtenirAvisParRendezVous(Long rendezVousId) {
        // Assuming RendezVous exists with id
        return avisRepository.findByRendezVous_Id(rendezVousId);
    }
    
    public List<Avis> obtenirAvisParService(Long serviceId) {
        // Assuming Service exists with id
        return avisRepository.findByRendezVous_Service_Id(serviceId);
    }
    
    public Double obtenirNoyenneLesNotes(Long serviceId) {
        List<Avis> avis = obtenirAvisParService(serviceId);
        if (avis.isEmpty()) {
            return 0.0;
        }
        return avis.stream()
                .mapToInt(a -> a.getNote())
                .average()
                .orElse(0.0);
    }
}
