-- 1. Insertion de l'Admin (Mot de passe : admin123)
INSERT IGNORE INTO `users` (`id`, `email`, `nom`, `password`, `role`, `statut`, `telephone`, `est_supprime`)
VALUES (1, 'admin', 'Administrateur', '$2a$10$vM9L1.9/W2uK2u1N/K.hO.o.G0S7V.v/U.6G.Y.6G.Y.6G.Y.6G', 'ADMIN', 'ACTIF', '0600000000', b'0');

INSERT IGNORE INTO `admins` (`id`)
VALUES (1);

-- 2. Insertion du Prestataire de test (Dr. Ayoub, Mot de passe : admin123)
INSERT IGNORE INTO `users` (`id`, `email`, `nom`, `password`, `role`, `statut`, `telephone`, `est_supprime`)
VALUES (4, 'dr.test@test.com', 'Dr. Ayoub Expert', '$2a$10$vM9L1.9/W2uK2u1N/K.hO.o.G0S7V.v/U.6G.Y.6G.Y.6G.Y.6G', 'PRESTATAIRE', 'ACTIF', '0600000000', b'0');

INSERT IGNORE INTO `prestataires` (`id`, `horaires`, `localisation`, `secteur`)
VALUES (4, '09:00-18:00', 'Rabat', 'Génie Logiciel');

-- 3. Insertion du Service de consultation
INSERT IGNORE INTO `services` (`id`, `nom`, `description`, `duree`, `prix`, `statut`, `prestataire_id`, `est_disponible`, `est_supprime`)
VALUES (1, 'Consultation DSE', 'Analyse', 30, 200.0, 'ACTIF', 4, b'1', b'0');

-- 4. Insertion des Créneaux disponibles
INSERT IGNORE INTO `creneaux` (`id`, `date_debut`, `date_fin`, `statut`, `prestataire_id`, `service_id`) VALUES
(1, '2026-05-12 10:00:00.000000', '2026-05-15 10:30:00.000000', 'DISPONIBLE', 4, 1),
(2, '2026-05-15 11:00:00.000000', '2026-05-15 11:30:00.000000', 'DISPONIBLE', 4, 1),
(3, '2026-05-13 15:00:00.000000', '2026-05-15 15:30:00.000000', 'DISPONIBLE', 4, 1),
(4, '2026-05-14 16:00:00.000000', '2026-05-15 11:30:00.000000', 'DISPONIBLE', 4, 1),
(5, '2026-05-17 11:00:00.000000', '2026-05-15 11:30:00.000000', 'DISPONIBLE', 4, 1),
(6, '2026-05-18 11:00:00.000000', '2026-05-15 11:30:00.000000', 'DISPONIBLE', 4, 1),
(7, '2026-05-19 11:00:00.000000', '2026-05-15 11:30:00.000000', 'DISPONIBLE', 4, 1),
(8, '2026-05-20 11:00:00.000000', '2026-05-15 11:30:00.000000', 'DISPONIBLE', 4, 1);
