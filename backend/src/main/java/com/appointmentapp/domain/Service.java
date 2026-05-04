package com.appointmentapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private Integer duree;

    @Column(nullable = false)
    private Double prix;

    @Column
    private Boolean estDisponible = true;

    @Column
    private Boolean estSupprime = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Creneau> creneaux = new HashSet<>();

    @OneToMany(mappedBy = "service")
    private Set<RendezVous> rendezVous = new HashSet<>();

    public void modifierPrix(Double nouveauPrix) {
        this.prix = nouveauPrix;
    }

    public void modifierDuree(Integer nouvelleDuree) {
        this.duree = nouvelleDuree;
    }
}
