package com.appointmentapp.domain;

import com.appointmentapp.domain.enums.StatutRDV;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rendez_vous")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutRDV statut;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "creneau_id", unique = true)
    private Creneau creneau;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "paiement_id", unique = true)
    private Paiement paiement;

    @OneToOne(mappedBy = "rendezVous")
    private Avis avis;

    public void confirmer() {
        this.statut = StatutRDV.CONFIRME;
    }

    public void annuler() {
        this.statut = StatutRDV.ANNULE;
    }

    public void terminer() {
        this.statut = StatutRDV.TERMINE;
    }

    public void modifierStatut(StatutRDV nouveauStatut) {
        this.statut = nouveauStatut;
    }
}
