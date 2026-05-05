package com.appointmentapp.service;

import com.appointmentapp.domain.Client;
import com.appointmentapp.domain.Creneau;
import com.appointmentapp.domain.Paiement;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.RendezVous;
import com.appointmentapp.domain.Service;
import com.appointmentapp.domain.enums.StatutPaiement;
import com.appointmentapp.domain.enums.StatutRDV;
import com.appointmentapp.repository.RendezVousRepository;
import com.appointmentapp.repository.PaiementRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final PaiementRepository paiementRepository;
    private final CreneauService creneauService;

    public RendezVous creerRendezVous(Client client, Prestataire prestataire, Service service, Creneau creneau, LocalDateTime date) {
        if (!creneauService.verifierDisponibilite(creneau)) {
            throw new IllegalStateException("Le créneau n'est pas disponible");
        }

        Paiement paiement = new Paiement();
        paiement.setDatePaiement(LocalDateTime.now());
        paiement.setMontant(service.getPrix());
        paiement.setStatut(StatutPaiement.EN_ATTENTE);
        paiement.setClient(client);
        paiement = paiementRepository.save(paiement);

        RendezVous rendezVous = new RendezVous();
        rendezVous.setDate(date != null ? date : LocalDateTime.now());
        rendezVous.setStatut(StatutRDV.EN_ATTENTE);
        rendezVous.setClient(client);
        rendezVous.setPrestataire(prestataire);
        rendezVous.setService(service);
        rendezVous.setCreneau(creneau);
        rendezVous.setPaiement(paiement);

        creneauService.reserver(creneau);
        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> obtenirRendezVousDuClient(Client client) {
        return rendezVousRepository.findByClient(client);
    }

    public List<RendezVous> obtenirRendezVousDuPrestataire(Prestataire prestataire) {
        return rendezVousRepository.findByPrestataire(prestataire);
    }

    public List<RendezVous> obtenirRendezVousParService(Service service) {
        return rendezVousRepository.findByService(service);
    }

    public RendezVous confirmer(RendezVous rendezVous) {
        rendezVous.confirmer();
        return rendezVousRepository.save(rendezVous);
    }

    public RendezVous annuler(RendezVous rendezVous) {
        rendezVous.annuler();
        if (rendezVous.getCreneau() != null) {
            creneauService.liberer(rendezVous.getCreneau());
        }
        return rendezVousRepository.save(rendezVous);
    }

    public RendezVous terminer(RendezVous rendezVous) {
        rendezVous.terminer();
        return rendezVousRepository.save(rendezVous);
    }
    
    public List<RendezVous> obtenirTousLesRendezVous() {
        return rendezVousRepository.findAll();
    }
    
    public java.util.Optional<RendezVous> findById(Long id) {
        return rendezVousRepository.findById(id);
    }
    
    public RendezVous save(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }
    
    public void delete(Long id) {
        rendezVousRepository.deleteById(id);
    }
}
