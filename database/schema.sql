-- Script de création de la base de données pour le portail de formation
-- Date: 2026-01-22

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------
-- Table `administrateurs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `administrateurs`;
CREATE TABLE `administrateurs` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(255) NOT NULL,
  `prenom` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `type` ENUM('SUPER_ADMIN', 'ADMIN_STANDARD', 'EDITEUR') NOT NULL DEFAULT 'ADMIN_STANDARD',
  `statut` ENUM('ACTIF', 'SUSPENDU') NOT NULL DEFAULT 'ACTIF', -- Exclus: EN_ATTENTE
  `avatar_url` VARCHAR(255) NULL,
  `telephone` VARCHAR(50) NULL,
  `derniere_connexion` DATETIME NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_unique` (`email` ASC)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `instructeurs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `instructeurs`;
CREATE TABLE `instructeurs` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nom_complet` VARCHAR(255) NOT NULL,
  `titre_professionnel` VARCHAR(255) NOT NULL,
  `organisation` VARCHAR(255) NULL,
  `biographie_courte` VARCHAR(500) NULL,
  `biographie_complete` TEXT NULL,
  `photo_url` VARCHAR(255) NULL,
  `site_web` VARCHAR(255) NULL,
  `linkedin_url` VARCHAR(255) NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` BIGINT UNSIGNED NULL,
  `nom` VARCHAR(255) NOT NULL,
  `slug` VARCHAR(255) NOT NULL,
  `description` VARCHAR(500) NULL,
  `icone_class` VARCHAR(100) NULL,
  `couleur_hex` VARCHAR(20) NULL,
  `ordre_affichage` INT NOT NULL DEFAULT 0,
  `est_actif` BOOLEAN NOT NULL DEFAULT TRUE,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `slug_unique` (`slug` ASC),
  INDEX `fk_categories_parent_idx` (`parent_id` ASC),
  CONSTRAINT `fk_categories_parent`
    FOREIGN KEY (`parent_id`)
    REFERENCES `categories` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `cours`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cours`;
CREATE TABLE `cours` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `administrateur_id` BIGINT UNSIGNED NOT NULL COMMENT 'Créateur ou responsable du cours',
  `instructeur_id` BIGINT UNSIGNED NOT NULL COMMENT 'Instructeur qui présente le cours',
  `categorie_id` BIGINT UNSIGNED NULL,
  `titre` VARCHAR(255) NOT NULL,
  `slug` VARCHAR(255) NOT NULL,
  `synopsis_court` VARCHAR(500) NULL,
  `description_complete` TEXT NULL,
  `objectifs_pedagogiques` JSON NULL,
  `public_cible` JSON NULL,
  `prerequis` JSON NULL,
  `duree_totale_minutes` INT NULL,
  `niveau` VARCHAR(50) NULL,
  `langue` ENUM('FR', 'EN', 'ES') NOT NULL DEFAULT 'FR',
  `format` ENUM('VIDEO', 'TEXTE', 'INTERACTIF', 'HYBRIDE') NOT NULL DEFAULT 'VIDEO',
  `est_certifiant` BOOLEAN NOT NULL DEFAULT FALSE,
  `statut` ENUM('BROUILLON', 'PROGRAMME', 'PUBLIE', 'ARCHIVE') NOT NULL DEFAULT 'BROUILLON', -- Exclus: EN_REVISION
  `date_publication` DATETIME NULL,
  `meta_title` VARCHAR(255) NULL,
  `meta_description` VARCHAR(500) NULL,
  `nombre_vues` BIGINT NOT NULL DEFAULT 0,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `slug_unique` (`slug` ASC),
  INDEX `fk_cours_administrateur_idx` (`administrateur_id` ASC),
  INDEX `fk_cours_instructeur_idx` (`instructeur_id` ASC),
  INDEX `fk_cours_categorie_idx` (`categorie_id` ASC),
  CONSTRAINT `fk_cours_administrateur`
    FOREIGN KEY (`administrateur_id`)
    REFERENCES `administrateurs` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cours_instructeur`
    FOREIGN KEY (`instructeur_id`)
    REFERENCES `instructeurs` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cours_categorie`
    FOREIGN KEY (`categorie_id`)
    REFERENCES `categories` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `media`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `media`;
CREATE TABLE `media` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cours_id` BIGINT UNSIGNED NOT NULL,
  `nom_fichier` VARCHAR(255) NOT NULL,
  `chemin_stockage` VARCHAR(500) NOT NULL,
  `url_publique` VARCHAR(500) NOT NULL,
  `type` ENUM('VIDEO_MP4', 'VIDEO_WEBM', 'IMG_JPG', 'IMG_PNG', 'DOC_PDF') NOT NULL,
  `taille_octets` BIGINT NOT NULL,
  `duree_secondes` INT NULL,
  `dimensions` VARCHAR(50) NULL,
  `alt_text` VARCHAR(255) NULL,
  `est_principal` BOOLEAN NOT NULL DEFAULT FALSE,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_media_cours_idx` (`cours_id` ASC),
  CONSTRAINT `fk_media_cours`
    FOREIGN KEY (`cours_id`)
    REFERENCES `cours` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;

SET FOREIGN_KEY_CHECKS = 1;
