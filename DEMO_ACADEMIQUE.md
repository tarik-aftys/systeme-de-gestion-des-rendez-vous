# Demo academique — Cycle de vie d'une reservation et administration

---

# Objectif pedagogique

Conformement a la consigne demandant de limiter le developpement a une sous-partie coherente, la demonstration se concentre sur le flux metier central du logiciel :

## Un parcours interessant d'un client et de l'administrateur

Ce perimetre valide les competences suivantes :

- Securite et authentification (JWT)
- Developpement Full-Stack (React / Spring Boot)
- Modelisation des donnees et persistance (JPA/Hibernate avec heritage)
- Interface utilisateur dynamique et simple

---

# Perimetre technique demontre

## Backend (Spring Boot)

### Authentification

```http
POST /api/auth/login
```

Generation du token JWT.

---

### Inscription publique

```http
POST /api/clients
```

Creation d'un utilisateur.

---

### Historique des rendez-vous

```http
GET /api/rendez-vous/client/{id}
```

Recuperation dynamique de l'historique.

---

### Creation d'une reservation

```http
POST /api/rendez-vous
```

Creation d'une reservation avec contrainte d'unicite sur les creneaux.

---

### Mise a jour des donnees

```http
PUT /api/clients/{id}
```

Modification des donnees utilisateur (Acces Admin).

---

### Suppression logique

```http
DELETE /api/clients/{id}
```

Soft Delete avec blocage automatique de l'authentification.

---

## Frontend (React)

- Gestion de l'etat global et stockage du JWT
- Rendu conditionnel des vues via un menu de navigation de demonstration
- Tunnel de reservation en plusieurs etapes :
  - Prestataire
  - Service
  - Creneau
  - Confirmation
- Tableau de bord administrateur avec edition en ligne (Inline Editing)

---

# Lancer l'environnement de demonstration

## 1. Base de donnees et outils

```bash
docker compose up -d
```

---

## 2. Backend

Le backend initialise automatiquement les donnees de test via `data.sql`.

```bash
cd backend
mvn clean spring-boot:run
```

---

## 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

---

# Scenario de presentation detaille

La demonstration est concue pour durer entre 3 et 5 minutes et montrer le fonctionnement complet de l'application en conditions reelles.

---

# Etape 1 — Introduction et preuve de securite (Admin)

1. Ouvrir l'application :

```text
http://localhost:5173
```

2. Ouvrir l'inspecteur du navigateur (`Network` / `Reseau`).

3. Se connecter avec le compte administrateur :

```text
Identifiant : admin
Mot de passe : admin123
```

4. Montrer la reponse de l'API contenant le token JWT.

5. Montrer la vue "Liste des clients" afin de prouver l'acces aux routes protegees.

---

# Etape 2 — Parcours Client (Inscription)

1. Aller sur "Inscription Client".

2. Remplir le formulaire avec de nouvelles donnees :

```text
Nom : Demo Etudiant
Email : demo@insea.ma
```

3. Valider l'inscription.

4. Se connecter immediatement avec ce nouveau compte.

5. Montrer le tableau de bord client vide afin de prouver que les donnees sont liees a l'utilisateur authentifie.

---

# Etape 3 — Tunnel de reservation (Cœur du metier)

1. Depuis l'espace client, cliquer sur "Nouvelle reservation".

2. Selectionner les donnees preparees :

```text
Prestataire : Dr. Ayoub
Service : Consultation DSE
Date : 20 mai 2026
Heure : 11:00
```

3. Confirmer la reservation.

4. Montrer l'ecran de succes.

5. Revenir au tableau de bord client et montrer que le bloc :

```text
Prochain Rendez-vous
```

est mis a jour dynamiquement avec les donnees de la base.

---

# Etape 4 — Gestion administrative et edition en ligne

1. Se deconnecter du compte client.

2. Se reconnecter avec le compte administrateur.

3. Ouvrir la vue "Liste".

4. Localiser le client fraichement cree.

5. Cliquer sur :

```text
Modifier
```

pour activer l'edition en ligne.

6. Modifier une information (exemple : numero de telephone).

7. Cliquer sur :

```text
Confirmer
```

8. Rafraichir la liste afin de montrer la persistance des modifications via la methode `PUT`.

---

# Etape 5 — Suppression logique et blocage d'acces

1. Depuis la vue Admin, cliquer sur :

```text
Supprimer
```

pour le client cree.

2. Accepter la boite de dialogue de confirmation.

3. Montrer que le client apparait comme :

```text
Supprime
```

dans la liste (Soft Delete).

4. Se deconnecter du compte administrateur.

5. Tenter de se reconnecter avec le compte du client supprime.

6. Montrer le message :

```text
403 Forbidden
```

afin de prouver que le backend verifie l'integrite du compte avant de generer un JWT.

---

# Ce qui est volontairement hors perimetre

- Les systemes de paiement en ligne
  - Simules dans la modelisation mais non executes
- L'envoi reel de notifications par email ou SMS
- L'interface dediee au prestataire
  - Seuls les espaces Client et Administrateur sont presentes afin de fluidifier la demonstration