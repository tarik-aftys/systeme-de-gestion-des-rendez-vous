package com.appointmentapp.repository;

import com.appointmentapp.domain.Avis;
import com.appointmentapp.domain.Prestataire;
import com.appointmentapp.domain.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {
    List<Avis> findByPrestataire(Prestataire prestataire);
    
    List<Avis> findByRendezVous(RendezVous rendezVous);
    
    List<Avis> findByRendezVous_Id(Long rendezVousId);
    
    List<Avis> findByRendezVous_Service_Id(Long serviceId);
}
