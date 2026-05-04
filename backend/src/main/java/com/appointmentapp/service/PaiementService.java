package com.appointmentapp.service;

import com.appointmentapp.domain.Paiement;
import com.appointmentapp.domain.enums.StatutPaiement;
import com.appointmentapp.repository.PaiementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaiementService {
    
    private final PaiementRepository paiementRepository;
    
    /**
     * Find all payments
     */
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }
    
    /**
     * Find a payment by ID
     */
    public Optional<Paiement> findById(Long id) {
        return paiementRepository.findById(id);
    }
    
    /**
     * Find payments by status
     */
    public List<Paiement> findByStatut(StatutPaiement statut) {
        return paiementRepository.findByStatut(statut);
    }
    
    /**
     * Find pending payments (not yet paid)
     */
    public List<Paiement> findPendingPayments() {
        return paiementRepository.findByStatut(StatutPaiement.EN_ATTENTE);
    }
    
    /**
     * Save or update a payment
     */
    public Paiement save(Paiement paiement) {
        return paiementRepository.save(paiement);
    }
    
    /**
     * Update payment status (e.g., mark as paid)
     */
    public Paiement updateStatus(Long id, StatutPaiement newStatus) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isPresent()) {
            paiement.get().setStatut(newStatus);
            if (newStatus == StatutPaiement.PAYE) {
                paiement.get().setDatePaiement(java.time.LocalDateTime.now());
            }
            return paiementRepository.save(paiement.get());
        }
        throw new RuntimeException("Payment not found with id: " + id);
    }
    
    /**
     * Delete a payment
     */
    public void delete(Long id) {
        paiementRepository.deleteById(id);
    }
}
