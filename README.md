# Système de gestion de rendez-vous en ligne

Plateforme de démonstration pour la gestion de prises de rendez-vous (backend Java / Spring Boot + frontend React). Ce dépôt contient le code source, la configuration Docker minimale et les scripts pour lancer l'application en local.

**Statut:** prototype — backend opérationnel, entités JPA et API de base implémentées.

**Dépôt:** [systeme-de-gestion-des-rendez-vous](https://github.com/tarik-aftys/systeme-de-gestion-des-rendez-vous)

**Arborescence principale**
- [backend](backend): Spring Boot 3 (Java 17), JPA/Hibernate, sécurité (JWT planifiée)
- [frontend](frontend): React 18 + Vite + TailwindCSS
- [docker-compose.yml](docker-compose.yml): PostgreSQL, Redis, Adminer

**Technologies**
- Java 17, Spring Boot 3.x
- Hibernate / JPA
- PostgreSQL 16
- React 18, Vite, TailwindCSS
- Docker / Docker Compose

## Prérequis
- Java 17
- Maven (ou utiliser le wrapper si présent)
- Node.js 18+ et npm
- Docker & Docker Compose (optionnel mais recommandé pour la DB)

## Installation & lancement (rapide)

1) Démarrer les services Docker (Postgres, Redis, Adminer)

```bash
docker compose up -d
```

2) Lancer le backend

```bash
cd backend
mvn clean spring-boot:run
```

- Le backend écoute par défaut sur `http://localhost:8080`.
- Point de santé: `http://localhost:8080/api/health` → renvoie `{"status":"UP"}`.

3) Lancer le frontend (dev)

```bash
cd frontend
npm install
npm run dev
```

## Base de données
- Connexion PostgreSQL (développement): `jdbc:postgresql://localhost:5435/appointment_app`
- Utilisateur / mot de passe: `appointment_user` / `appointment_password`
- Adminer UI: `http://localhost:8081` (pour vérifier les tables)

## Exécuter les tests

Backend (JUnit):

```bash
cd backend
mvn test
```

Frontend (si tests ajoutés):

```bash
cd frontend
npm test
```

## Structure du backend
- `com.appointmentapp.domain`: entités JPA (User, Client, Prestataire, Service, Creneau, RendezVous, Paiement, Avis, Notification)
- `com.appointmentapp.controller`: endpoints REST
- `com.appointmentapp.config`: configuration Spring (CORS, sécurité)
