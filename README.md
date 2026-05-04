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

## API — Endpoints rapides

Base URL: `http://localhost:8080`

### Clients
- GET  /api/clients
- GET  /api/clients/{id}
- POST /api/clients
	- Body JSON: {"email","nom","password","telephone","adresse","dateNaissance"}
- PUT  /api/clients/{id}
- DELETE /api/clients/{id} (soft)

### Users
- GET  /api/users
- GET  /api/users/{id}
- POST /api/users
- PUT  /api/users/{id}
- DELETE /api/users/{id}

### Prestataires
- GET  /api/prestataires
- GET  /api/prestataires/{id}
- POST /api/prestataires
- PUT  /api/prestataires/{id}
- DELETE /api/prestataires/{id} (soft)

### Services
- GET  /api/services
- GET  /api/services/available
- GET  /api/services/{id}
- GET  /api/services/search?name=xxx
- POST /api/services  (Body: {"nom","description","prix","duree","estDisponible"})
- PUT  /api/services/{id}
- PATCH /api/services/{id}/availability?available=true
- DELETE /api/services/{id}

### Créneaux
- GET  /api/creneaux
- GET  /api/creneaux/{id}
- GET  /api/creneaux/prestataire/{prestataireId}
- POST /api/creneaux  (Body: {"dateDebut","dateFin","prestataireId","serviceId"})
- DELETE /api/creneaux/{id}

### Rendez‑Vous
- GET  /api/rendez-vous
- GET  /api/rendez-vous/{id}
- GET  /api/rendez-vous/client/{clientId}
- POST /api/rendez-vous  (Body: {"date","clientId","prestataireId","serviceId","creneauId"})
- PUT  /api/rendez-vous/{id}/cancel
- DELETE /api/rendez-vous/{id}

### Paiements
- GET  /api/paiements
- GET  /api/paiements/{id}
- GET  /api/paiements/status/pending
- GET  /api/paiements/status/{statut}
- POST /api/paiements  (Body: {"montant","datePaiement","statut","methodePaiement","rendezVousId"})
- PUT  /api/paiements/{id}
- PATCH /api/paiements/{id}/status?newStatus=PAYE
- DELETE /api/paiements/{id}

### Avis
- GET  /api/avis
- GET  /api/avis/{id}
- GET  /api/avis/rendez-vous/{rendezVousId}
- GET  /api/avis/service/{serviceId}
- GET  /api/avis/service/{serviceId}/average-rating
- POST /api/avis  (Body: {"note","commentaire","clientId","rendezVousId","serviceId"})
- PUT  /api/avis/{id}
- DELETE /api/avis/{id}

### Notifications
- GET  /api/notifications/user/{userId}
- POST /api/notifications  (Body: {"userId","type","contenu"})
- PATCH /api/notifications/{id}/read

## Exemples curl

Créer un client:
```bash
curl -X POST http://localhost:8080/api/clients \
	-H "Content-Type: application/json" \
	-d '{"email":"alice@example.com","nom":"Alice","password":"secret123","telephone":"0600000000","adresse":"1 rue Exemple","dateNaissance":"1995-04-12"}'
```

Lister les services disponibles:
```bash
curl http://localhost:8080/api/services/available
```

Créer un rendez‑vous:
```bash
curl -X POST http://localhost:8080/api/rendez-vous \
	-H "Content-Type: application/json" \
	-d '{"date":"2026-05-12T14:30:00","clientId":1,"prestataireId":2,"serviceId":5,"creneauId":10}'
```

Marquer un paiement comme payé:
```bash
curl -X PATCH "http://localhost:8080/api/paiements/42/status?newStatus=PAYE"
```

---

Pour ajouter ce fichier au commit:
```bash
git add README.md
git commit -m "docs: Add API endpoints overview"
git push origin main
```
