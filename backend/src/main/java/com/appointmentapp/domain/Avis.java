package com.appointmentapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "avis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer note;

    @Column(nullable = false)
    private String commentaire;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String statut;

    @OneToOne(optional = false)
    @JoinColumn(name = "rendez_vous_id", unique = true)
    private RendezVous rendezVous;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    public void ajouterAvis() {
        // Ajout d'un avis
    }

    public void modifierAvis() {
        // Modification d'un avis
    }

    public void supprimerAvis() {
        // Suppression d'un avis
    }
}
