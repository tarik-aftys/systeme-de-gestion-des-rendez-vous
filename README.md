# Systeme de gestion de rendez-vous en ligne

Plateforme de demonstration pour la gestion de prises de rendez-vous (backend Java / Spring Boot + frontend React). Ce depot contient le code source, la configuration Docker minimale et les scripts pour lancer l'application en local.

**Statut :** MVP fonctionnel — backend operationnel, entites JPA, API securisee par JWT, tunnel de reservation frontend et tableau de bord d'administration complet.

**Depot :** https://github.com/tarik-aftys/systeme-de-gestion-des-rendez-vous

---

## Arborescence principale

- `backend` : Spring Boot 3 (Java 17), JPA/Hibernate, Spring Security (JWT operationnel).
- `frontend` : React 18 + Vite + TailwindCSS (Interfaces Admin et Client).
- `docker-compose.yml` : MySQL, Redis, Adminer.

---

## Technologies

- Java 17
- Spring Boot 3.x
- Hibernate / JPA
- Spring Security & JWT (JSON Web Tokens)
- MySQL 8
- React 18
- Vite
- TailwindCSS
- Docker / Docker Compose

---

## Fonctionnalites implementees

- Authentification JWT : Connexion securisee et blocage automatique des comptes supprimes.
- Espace Client : Tableau de bord dynamique et tunnel de reservation complet (choix du prestataire, service, creneau et confirmation).
- Espace Administrateur : Tableau de bord avec edition en ligne (inline editing) des informations clients et suppression logique (soft delete).
- Base de donnees automatisee : Initialisation automatique des donnees de test (prestataires, services, creneaux) au lancement du backend.

---

# Installation & lancement

## 1. Demarrer les services Docker

```bash
docker compose up -d
```

---

## 2. Lancer le backend

Le backend utilise un fichier `data.sql` pour populer automatiquement la base de donnees avec des comptes de test (Admin, Prestataire, etc.) et des creneaux.

```bash
cd backend
mvn clean spring-boot:run
```

Le backend ecoute par defaut sur :

```text
http://localhost:8080
```

### Point de sante

```text
http://localhost:8080/api/health
```

Retour attendu :

```json
{"status":"UP"}
```

---

## 3. Lancer le frontend

```bash
cd frontend
npm install
npm run dev
```

L'interface est accessible sur :

```text
http://localhost:5173
```

---

# Base de donnees

L'application utilise une strategie d'heritage `JOINED` pour les utilisateurs (`Users`, `Clients`, `Prestataires`).

## Connexion MySQL

```text
jdbc:mysql://localhost:3306/appointment_app
```

### Identifiants

```text
Utilisateur : appointment_user
Mot de passe : appointment_password
```

### Adminer

```text
http://localhost:8081
```

---

# API — Endpoints principaux

## Base URL

```text
http://localhost:8080/api
```

---

## Authentification & securite

### Connexion

```http
POST /auth/login
```

Retourne un token JWT.

La majorite des routes suivantes necessitent le header HTTP :

```http
Authorization: Bearer <token>
```

---

## Clients

```http
GET    /clients
GET    /clients/{id}
POST   /clients
PUT    /clients/{id}
DELETE /clients/{id}
```

- `GET /clients` : Administration uniquement
- `POST /clients` : Inscription publique
- `PUT /clients/{id}` : Modification (Admin)
- `DELETE /clients/{id}` : Suppression logique (Admin)

---

## Prestataires & Services

```http
GET /prestataires
GET /services/available
GET /services/search?name=xxx
```

---

## Creneaux & Rendez-Vous

```http
GET  /creneaux/prestataire/{prestataireId}
GET  /rendez-vous/client/{clientId}
POST /rendez-vous
```

- `GET /rendez-vous/client/{clientId}` : Historique client
- `POST /rendez-vous` : Creation de reservation

---

# Exemples cURL

## Authentification (recuperation du JWT)

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

---

## Creer un client (inscription publique)

```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"email":"alice@example.com","nom":"Alice","password":"secret123","telephone":"0600000000","adresse":"1 rue Exemple","dateNaissance":"1995-04-12"}'
```

---

## Lister les clients (JWT Admin requis)

```bash
curl http://localhost:8080/api/clients \
  -H "Authorization: Bearer VOTRE_TOKEN_ICI"
```

---

# Tests d'integration

Les tests d'integration couvrent les flux principaux de l'API.

## Lancer tous les tests

```bash
cd backend
mvn clean verify
```
### Lancer uniquement les tests d'intégration

```bash
cd backend
mvn test -Dtest=AppointmentApiIntegrationTest
```


## Lancer les tests avec H2 en memoire

```bash
cd backend
mvn test -Dspring.profiles.active=test
```

---

### Cas de test couverts

- ✅ Créer un client valide
- ✅ Récupérer tous les clients (public)
- ✅ Créer un service (résout le prestataire)
- ✅ Valider le refus des POST sans authentification
- ✅ Rejeter les validations échouées (email invalide, etc.)
- ✅ Créer un créneau (valide dates et entités liées)
- ✅ Récupérer les paiements (public)
- ✅ Vérifier la sécurité des endpoints (GET public, POST protégé)
- ✅ Créer un utilisateur système

---

Pour ajouter ce fichier au commit:
```bash
git add README.md
git commit -m "docs: Add API endpoints overview"
git push origin main
```
