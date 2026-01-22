# Description du Schéma de Base de Données

Ce document décrit la structure de la base de données MySQL pour le portail de formation.

## Tables

### 1. Administrateurs (`administrateurs`)
Stocke les comptes des administrateurs du système.
*   **id**: Identifiant unique (PK, AUTO_INCREMENT).
*   **nom**: Nom de famille.
*   **prenom**: Prénom.
*   **email**: Adresse email unique (Unique Index).
*   **password_hash**: Hash du mot de passe.
*   **type**: Rôle de l'administrateur (`SUPER_ADMIN`, `ADMIN_STANDARD`, `EDITEUR`).
*   **statut**: Statut du compte (`ACTIF`, `SUSPENDU`). *Note: `EN_ATTENTE` a été exclus.*
*   **avatar_url**: URL de l'image de profil.
*   **telephone**: Numéro de téléphone.
*   **derniere_connexion**: Date et heure de la dernière connexion.
*   **created_at**, **updated_at**, **deleted_at**: Timestamps pour le suivi et le soft delete.

### 2. Instructeurs (`instructeurs`)
Stocke les profils des instructeurs qui présentent les cours.
*   **id**: Identifiant unique (PK, AUTO_INCREMENT).
*   **nom_complet**: Nom complet de l'instructeur.
*   **titre_professionnel**: Titre (ex: "Expert MySQL").
*   **organisation**: Organisation de rattachement.
*   **biographie_courte**: Bio résumée.
*   **biographie_complete**: Bio détaillée.
*   **photo_url**: URL de la photo.
*   **site_web**, **linkedin_url**: Liens sociaux.
*   **created_at**, **updated_at**: Timestamps.

### 3. Catégories (`categories`)
Structure hiérarchique pour classer les cours.
*   **id**: Identifiant unique (PK, AUTO_INCREMENT).
*   **parent_id**: Référence à la catégorie parente (FK vers `categories.id`, Nullable).
*   **nom**: Nom de la catégorie.
*   **slug**: Identifiant URL unique (Unique Index).
*   **description**: Description de la catégorie.
*   **icone_class**: Classe CSS pour l'icône.
*   **couleur_hex**: Code couleur.
*   **ordre_affichage**: Entier pour le tri.
*   **est_actif**: Booléen pour activer/désactiver.
*   **created_at**: Timestamp.

### 4. Cours (`cours`)
Le cœur du système, stockant les informations sur les formations.
*   **id**: Identifiant unique (PK, AUTO_INCREMENT).
*   **administrateur_id**: L'administrateur responsable/créateur (FK vers `administrateurs.id`).
*   **instructeur_id**: L'instructeur qui présente le cours (FK vers `instructeurs.id`).
*   **categorie_id**: La catégorie principale (FK vers `categories.id`).
*   **titre**: Titre du cours.
*   **slug**: URL unique (Unique Index).
*   **synopsis_court**, **description_complete**: Textes descriptifs.
*   **objectifs_pedagogiques**, **public_cible**, **prerequis**: Champs JSON pour les listes d'items.
*   **duree_totale_minutes**: Durée en minutes.
*   **niveau**: Niveau de difficulté.
*   **langue**: Langue (`FR`, `EN`, `ES`).
*   **format**: Format pédagogique (`VIDEO`, `TEXTE`, `INTERACTIF`, `HYBRIDE`).
*   **est_certifiant**: Booléen.
*   **statut**: État du cours (`BROUILLON`, `PROGRAMME`, `PUBLIE`, `ARCHIVE`). *Note: `EN_REVISION` a été exclus.*
*   **date_publication**, **meta_title**, **meta_description**, **nombre_vues**: Métadonnées et SEO.
*   **created_at**, **updated_at**, **deleted_at**: Timestamps.

### 5. Media (`media`)
Ressources multimédias associées aux cours.
*   **id**: Identifiant unique (PK, AUTO_INCREMENT).
*   **cours_id**: Le cours auquel ce média appartient (FK vers `cours.id`).
*   **nom_fichier**: Nom original.
*   **chemin_stockage**: Chemin interne.
*   **url_publique**: URL d'accès.
*   **type**: Type de média (`VIDEO_MP4`, `VIDEO_WEBM`, `IMG_JPG`, `IMG_PNG`, `DOC_PDF`). *Note: `TypeAction` a été exclus.*
*   **taille_octets**, **duree_secondes**, **dimensions**: Métadonnées techniques.
*   **alt_text**: Texte alternatif pour l'accessibilité.
*   **est_principal**: Si c'est le média principal du cours (ex: image de couverture).
*   **created_at**: Timestamp.

## Contraintes et Relations

### Clés Étrangères (Foreign Keys)
*   **cours.administrateur_id** -> `administrateurs.id`: `ON DELETE RESTRICT`, `ON UPDATE CASCADE`.
*   **cours.instructeur_id** -> `instructeurs.id`: `ON DELETE RESTRICT`, `ON UPDATE CASCADE`.
*   **cours.categorie_id** -> `categories.id`: `ON DELETE SET NULL`, `ON UPDATE CASCADE`.
*   **media.cours_id** -> `cours.id`: `ON DELETE CASCADE`, `ON UPDATE CASCADE` (La suppression d'un cours supprime ses médias).
*   **categories.parent_id** -> `categories.id`: `ON DELETE SET NULL`, `ON UPDATE CASCADE`.

### Index Uniques
*   `administrateurs.email`
*   `categories.slug`
*   `cours.slug`

### Enums
Les énumérations MySQL sont utilisées pour garantir l'intégrité des données pour les champs à valeurs finies (`type`, `statut`, `langue`, `format`).
