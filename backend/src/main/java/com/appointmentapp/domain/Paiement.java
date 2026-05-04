package com.appointmentapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.appointmentapp.domain.enums.StatutPaiement;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime datePaiement;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(mappedBy = "paiement")
    private RendezVous rendezVous;

    public boolean estPayer() {
        // Verification si le paiement est effectue
        return true;
    }

    public void genererFacture() {
        // Generation de la facture
    }

    public void rembourser() {
        // Remboursement du paiement
    }
}
