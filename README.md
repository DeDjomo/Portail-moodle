# Portal Project

Ce projet est une plateforme de formation en ligne comprenant un backend Spring Boot et une base de données MySQL.

## Structure du Projet

- `backend/`: Code source de l'application Spring Boot.
- `database/`: Scripts SQL et documentation de la base de données.
- `docker-compose.yml`: Configuration pour lancer l'environnement complet.

## Prérequis

- Docker et Docker Compose installés sur votre machine.

## Lancer le Projet

Pour démarrer l'application et la base de données, exécutez la commande suivante à la racine du projet :

```bash
docker-compose up -d --build
```

Cette commande va :
1.  Lancer le conteneur MySQL (`portal_db`).
2.  Construire et lancer le conteneur Backend (`portal_backend`).

## Accéder à l'Application

Une fois les conteneurs démarrés, vous pouvez accéder à :

- **API & Swagger UI** : [http://localhost:9080/api/](http://localhost:9080/api/)
    - Note : Toutes les URLs de l'API sont préfixées par `/api`.
    - L'accès root `/api/` redirige vers la documentation Swagger.

- **Base de Données** : Port `3307` (mappé vers le 3306 du conteneur).
    - User : `portal_user`
    - Password : `portal_password`
    - Database : `portal_db`

## Arrêter le Projet

To stop the containers:

```bash
docker-compose down
```
