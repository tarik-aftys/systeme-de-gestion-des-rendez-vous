package com.appointmentapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String contenu;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private LocalDateTime dateEnvoi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public void envoyer() {
        // Envoi de la notification
    }

    public void marquerCommeLue() {
        this.statut = "LUE";
    }
}
