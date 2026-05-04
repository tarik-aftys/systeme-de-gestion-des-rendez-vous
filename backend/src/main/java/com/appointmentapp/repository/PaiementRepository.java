package com.appointmentapp.repository;

import com.appointmentapp.domain.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
	java.util.List<Paiement> findByStatut(com.appointmentapp.domain.enums.StatutPaiement statut);
}
