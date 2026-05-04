package com.appointmentapp.repository;

import com.appointmentapp.domain.Service;
import com.appointmentapp.domain.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByPrestataire(Prestataire prestataire);
    
    List<Service> findByEstDisponibleIsTrueAndEstSupprimeIsFalse();
    
    List<Service> findByNomContainingIgnoreCase(String nom);
}
