# Tests d'intégration — API de gestion de rendez-vous

Ce document décrit en détail les tests d'intégration présents dans `backend/src/test/java/com/appointmentapp/integration/AppointmentApiIntegrationTest.java`.

Contexte général
- Environnement de test : SpringBootTest (port aléatoire), base H2 en mémoire (config `application-test.properties`).
- Client HTTP de test : `TestRestTemplate`.
- Sécurité : HTTP Basic (admin/admin123) nécessaire pour les opérations en écriture.
- Objectif : valider endpoints REST, sécurité, validation des DTOs, et comportements métier minimaux.

Commandes pour exécuter les tests
- Exécuter la suite complète :

```bash
cd "systéme de gestion de rendez-vous\\backend"
mvn test
```

- Exécuter uniquement les tests d'intégration :

```bash
mvn -Dtest=AppointmentApiIntegrationTest test
```

- Exécuter un seul test (ex. création client) :

```bash
mvn -Dtest=AppointmentApiIntegrationTest#testCreateClient test
```

Résumé détaillé des 14 tests

1) Test 1 — Create a Client
- But : Vérifier la création d'un `Client` via `POST /api/clients`.
- Setup : construit un `ClientCreateDTO` complet (email, nom, password, telephone, adresse, dateNaissance).
- Requête : `POST /api/clients` avec Basic auth `admin:admin123`.
- Attendu : `201 Created` et `ClientDTO` renvoyé contenant `id` et champs renseignés.
- Assertions : code HTTP, body non-null ; le test imprime le corps si le status est inattendu pour faciliter le debug.
- Note : corrigé pour définir `RoleUser.CLIENT` et éviter violation de contrainte NOT NULL.

2) Test 2 — Retrieve all Clients
- But : Valider `GET /api/clients` (endpoint public).
- Requête : `GET /api/clients` sans auth.
- Attendu : `200 OK` et tableau JSON de `ClientDTO`.

3) Test 3 — Create a Service
- But : Créer un `Service` lié à un `Prestataire` via `POST /api/services`.
- Setup : `ServiceCreateDTO` (nom, description, prix, duree, estDisponible, prestataireId).
- Requête : `POST /api/services` avec Basic auth.
- Attendu : soit `201 Created` (prestataire existant) soit `404 Not Found` (prestataire manquant).
- Assertions : si 201 -> vérifie `id` et `nom`; sinon s'assure du 404.

4) Test 4 — Retrieve all Services
- But : Valider `GET /api/services` (public).
- Requête : `GET /api/services`.
- Attendu : `200 OK` et tableau JSON.

5) Test 5 — POST without Authentication Is Rejected
- But : Confirmer que les opérations en écriture nécessitent auth.
- Requête : `POST /api/clients` sans auth.
- Attendu : `401 Unauthorized`.

6) Test 6 — Create Client with Invalid Email
- But : Valider la contrainte `@Email` sur `ClientCreateDTO`.
- Requête : `POST /api/clients` (email invalide) avec Basic auth.
- Attendu : `400 Bad Request`.

7) Test 7 — Create a Creneau
- But : Créer un créneau (`Creneau`) via `POST /api/creneaux`.
- Setup : `CreneauCreateDTO` (dateDebut, dateFin, prestataireId, serviceId).
- Requête : `POST /api/creneaux` avec Basic auth.
- Attendu : `201 Created` si prestataire/service présents, sinon `404 Not Found`.

8) Test 8 — Retrieve Payments
- But : Valider `GET /api/paiements` (public).
- Requête : `GET /api/paiements`.
- Attendu : `200 OK` et tableau JSON.

9) Test 9 — Retrieve Reviews
- But : Valider `GET /api/avis` (public).
- Requête : `GET /api/avis`.
- Attendu : `200 OK` et tableau JSON.

10) Test 10 — Update Payment With Invalid Status
- But : Vérifier gestion des mises à jour de statut invalides.
- Requête : `PATCH /api/paiements/1/status?newStatus=INVALID` avec Basic auth.
- Attendu : Exception ou erreur ; le test capture l'exception et vérifie qu'une erreur est bien levée.

11) Test 11 — GET Requests Are Public
- But : Vérifier que les GET sous `/api/**` restent accessibles sans auth.
- Requêtes : `GET /api/clients`, `GET /api/services`, `GET /api/paiements`.
- Attendu : `200 OK` pour chacun.

12) Test 12 — Create User (System Admin)
- But : Créer un utilisateur système via `POST /api/users`.
- Setup : `UserCreateDTO` avec `role=RoleUser.ADMIN`.
- Requête : `POST /api/users` avec Basic auth.
- Attendu : `201 Created` + `UserDTO` avec `id`.

13) Test 13 — Retrieve User By ID
- But : `GET /api/users/1` (test tolérant si l'utilisateur n'existe pas).
- Requête : `GET /api/users/1` sans auth.
- Attendu : `200 OK` si présent ou `404 Not Found`.

14) Test 14 — Health Endpoint
- But : Vérifier disponibilité minimale via `GET /api/health`.
- Requête : `GET /api/health`.
- Attendu : `200 OK` ou `404 Not Found` (tolérancement accepté).

Bonnes pratiques et debug
- Pour plus de précision, ajouter des fixtures `@BeforeEach` qui créent : prestataire, service, client de test, etc., afin de rendre les tests déterministes.
- Pour afficher plus de logs lors de l'exécution, activez : `logging.level.com.appointmentapp=DEBUG` dans `application-test.properties`.
- Si vous voulez exécuter avec sortie complète et stacktrace Maven :

```bash
mvn -Dtest=AppointmentApiIntegrationTest test -e -X
```

Fichier de référence : `backend/src/test/java/com/appointmentapp/integration/AppointmentApiIntegrationTest.java`.

---
Fichier généré automatiquement par l'agent — si tu veux que j'ajoute ce fichier au commit git, dis-le et je l'ajouterai et pousserai.
