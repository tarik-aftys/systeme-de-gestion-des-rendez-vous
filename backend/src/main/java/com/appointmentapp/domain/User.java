package com.appointmentapp.domain;

import com.appointmentapp.domain.enums.RoleUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String password;

    @Column
    private String telephone;

    @Column(nullable = false)
    private Boolean estSupprime = Boolean.FALSE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Column(nullable = false)
    private String statut;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications = new HashSet<>();

    public void seConnecter() {
        // Implementation de la connexion
    }

    public void seDeconnecter() {
        // Implementation de la deconnexion
    }

    public void modifierProfil() {
        // Implementation de la modification du profil
    }
}
