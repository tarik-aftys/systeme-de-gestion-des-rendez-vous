package com.appointmentapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prestataires")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Prestataire extends User {
    @Column(nullable = false)
    private String secteur;

    @Column(nullable = false)
    private String localisation;

    @Column(nullable = false)
    private String horaires;

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Service> services = new HashSet<>();

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Creneau> creneaux = new HashSet<>();

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RendezVous> rendezVous = new HashSet<>();

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Avis> avis = new HashSet<>();

    public void ajouterService(Service service) {
        services.add(service);
    }

    public void modifierService(Service service) {
        // Modification du service
    }

    public void ajouterCreneau(Creneau creneau) {
        creneaux.add(creneau);
    }

    public void consulterRendezVous() {
        // Consultation des rendez-vous
    }
}
