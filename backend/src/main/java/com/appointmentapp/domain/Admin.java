package com.appointmentapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    public void consulterDashboard() {
        // Consultation du dashboard
    }

    public void suspendreUtilisateur(User user) {
        // Suspension d'un utilisateur
    }

    public void gererUtilisateurs() {
        // Gestion des utilisateurs
    }

    public void validerPrestataires() {
        // Validation des prestataires
    }

    public void modererAvis() {
        // Moderation des avis
    }
}
