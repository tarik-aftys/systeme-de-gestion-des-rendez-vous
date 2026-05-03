package com.appointmentapp.repository;

import com.appointmentapp.domain.Creneau;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.Service;
import com.appointmentapp.domain.enums.StatutCreneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    List<Creneau> findByPrestataire(Prestataire prestataire);
    List<Creneau> findByService(Service service);
    List<Creneau> findByPrestataireAndStatut(Prestataire prestataire, StatutCreneau statut);
    List<Creneau> findByPrestataireAndDateDebutBetween(Prestataire prestataire, LocalDateTime start, LocalDateTime end);
}
