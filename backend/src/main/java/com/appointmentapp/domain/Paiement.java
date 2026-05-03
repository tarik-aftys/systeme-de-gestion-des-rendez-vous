package com.appointmentapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Long montant;

    @Column(nullable = false)
    private String nCompte;

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
