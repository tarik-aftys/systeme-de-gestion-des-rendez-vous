# Système de gestion de prise de rendez-vous en ligne

Monorepo simple avec:
- `backend/`: Spring Boot 3 + Java 17
- `frontend/`: React 18 + Vite + TailwindCSS

## Lancement prévu

Backend:
```bash
cd backend
mvn spring-boot:run
```

Frontend:
```bash
cd frontend
npm install
npm run dev
```

## Étape en cours

Le socle technique est initialisé. La prochaine itération ajoutera les entités JPA, les DTO, puis l’authentification JWT.
