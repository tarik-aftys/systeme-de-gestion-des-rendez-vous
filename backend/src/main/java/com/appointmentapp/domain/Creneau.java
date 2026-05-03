package com.appointmentapp.domain;

import com.appointmentapp.domain.enums.StatutCreneau;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "creneaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Creneau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutCreneau statut;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToOne(mappedBy = "creneau")
    private RendezVous rendezVous;

    public boolean verifierDisponibilite() {
        return statut == StatutCreneau.DISPONIBLE;
    }

    public void reserver() {
        if (statut == StatutCreneau.DISPONIBLE) {
            this.statut = StatutCreneau.RESERVE;
        }
    }

    public void liberer() {
        if (statut == StatutCreneau.RESERVE) {
            this.statut = StatutCreneau.DISPONIBLE;
        }
    }
}
