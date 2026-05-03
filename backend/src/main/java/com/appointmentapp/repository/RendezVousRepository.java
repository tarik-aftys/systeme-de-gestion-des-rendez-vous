package com.appointmentapp.repository;

import com.appointmentapp.domain.RendezVous;
import com.appointmentapp.domain.Client;
import com.appointmentapp.domain.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByClient(Client client);
    List<RendezVous> findByPrestataire(Prestataire prestataire);
}
