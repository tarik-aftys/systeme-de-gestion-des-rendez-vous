# Démo académique — Sous-partie cohérente (Backend + Frontend)

## Objectif pédagogique
Conformément à la consigne _« limiter le développement à une seule sous-partie cohérente »_, la démonstration se concentre sur **un seul module** :

**Gestion des clients**
1. Authentification JWT (admin)
2. Création d’un client
3. Consultation de la liste des clients

Ce périmètre est volontairement réduit pour privilégier la **modélisation**, la **conception API**, la **sécurité**, et le **flux end-to-end**.

---

## Pourquoi cette sous-partie est cohérente
- Elle relie un **cas métier simple** (gérer les clients) à un flux complet interface → API → base.
- Elle couvre les briques essentielles sans surcharger le projet :
  - sécurité (JWT),
  - validation des données,
  - persistance JPA,
  - interface utilisateur basique.
- Elle est démontrable en quelques minutes devant un encadrant.

---

## Périmètre technique (seulement ce qui est montré)

### Backend
- `POST /api/auth/login` : obtention du token JWT.
- `POST /api/clients` : création client (requiert JWT).
- `GET /api/clients` : consultation de la liste des clients.

### Frontend
- Écran unique dans `frontend/src/App.jsx` avec :
  - formulaire de login,
  - formulaire de création client,
  - tableau de liste des clients.

---

## Lancer la démo

### 1) Lancer le backend
```bash
cd backend
mvn spring-boot:run
```

### 2) Lancer le frontend
```bash
cd frontend
npm install
npm run dev
```

### 3) Ouvrir l’interface
- URL frontend : `http://localhost:5173`
- API backend : `http://localhost:8080/api`

---

## Scénario de présentation (3-5 minutes)
1. Expliquer le périmètre choisi (une seule sous-partie : gestion des clients).
2. Se connecter via `admin / admin123`.
3. Montrer que le JWT est obtenu.
4. Créer un client via formulaire.
5. Rafraîchir / observer la liste des clients mise à jour.
6. Conclure sur les points de conception validés (sécurité, validation, architecture couches).

---

## Ce qui est volontairement hors périmètre
- Parcours complet de réservation multi-étapes.
- Tableaux de bord avancés par rôle.
- Intégration complète de tous les modules du domaine.

Ce choix respecte la logique académique : **mieux démontrer une partie cohérente que survoler tout le projet**.
