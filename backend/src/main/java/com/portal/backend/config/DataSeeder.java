package com.portal.backend.config;

import com.portal.backend.entity.*;
import com.portal.backend.repository.*;
import com.portal.backend.util.PasswordUtil;
import com.portal.backend.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

        private final AdministrateurRepository adminRepository;
        private final InstructeurRepository instructeurRepository;
        private final CategorieRepository categorieRepository;
        private final CoursRepository coursRepository;
        private final MediaRepository mediaRepository;

        private static final String BASE_URL = "http://localhost:9080/api/uploads/";

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                if (adminRepository.count() == 0) {
                        seedData();
                }
        }

        private void seedData() {
                // Admins
                Administrateur superAdmin = Administrateur.builder()
                                .nom("Admin")
                                .prenom("Super")
                                .email("superadmin@portal.com")
                                .passwordHash(PasswordUtil.hashPassword("supersecret"))
                                .type(AdminType.SUPER_ADMIN)
                                .statut(AdminStatus.ACTIF)
                                .build();
                adminRepository.save(superAdmin);

                Administrateur adminStd = Administrateur.builder()
                                .nom("Doe")
                                .prenom("John")
                                .email("johndoe@portal.com")
                                .passwordHash(PasswordUtil.hashPassword("password"))
                                .type(AdminType.ADMIN_STANDARD)
                                .statut(AdminStatus.ACTIF)
                                .build();
                adminRepository.save(adminStd);

                // Instructeurs
                Instructeur instructeur1 = Instructeur.builder()
                                .nomComplet("Professeur X")
                                .titreProfessionnel("Expert Java")
                                .organisation("University of Code")
                                .biographieCourte("Passionné par le code propre.")
                                .biographieComplete("Une longue carrière dans le développement...")
                                .build();
                instructeurRepository.save(instructeur1);

                Instructeur instructeur2 = Instructeur.builder()
                                .nomComplet("Dr. Marie Curie")
                                .titreProfessionnel("Data Scientist")
                                .organisation("AI Labs")
                                .biographieCourte("Experte en Machine Learning.")
                                .biographieComplete("Docteur en sciences des données...")
                                .build();
                instructeurRepository.save(instructeur2);

                // Categories
                Categorie devCat = Categorie.builder()
                                .nom("Développement")
                                .slug(SlugUtil.toSlug("Développement"))
                                .description("Cours de programmation")
                                .ordreAffichage(1)
                                .build();
                categorieRepository.save(devCat);

                Categorie javaCat = Categorie.builder()
                                .nom("Java Spring")
                                .slug(SlugUtil.toSlug("Java Spring"))
                                .parent(devCat)
                                .description("Framework Spring Boot")
                                .ordreAffichage(1)
                                .build();
                categorieRepository.save(javaCat);

                Categorie gestionCat = Categorie.builder()
                                .nom("Gestion de Projet")
                                .slug(SlugUtil.toSlug("Gestion de Projet"))
                                .description("Cours de management et gestion")
                                .ordreAffichage(2)
                                .build();
                categorieRepository.save(gestionCat);

                // ========== COURS 1: Spring Boot ==========
                Cours coursJava = Cours.builder()
                                .administrateur(adminStd)
                                .instructeur(instructeur1)
                                .categorie(javaCat)
                                .titre("Maîtriser Spring Boot")
                                .slug(SlugUtil.toSlug("Maîtriser Spring Boot"))
                                .synopsisCourt("Apprenez à créer des API REST professionnelles.")
                                .descriptionComplete(
                                                "Ce cours couvre Spring Boot de A à Z, des bases aux microservices.")
                                .objectifsPedagogiques(
                                                "[\"Comprendre Spring\", \"Créer une API REST\", \"Gérer les bases de données\"]")
                                .publicCible("[\"Développeurs Java\", \"Architectes logiciels\"]")
                                .prerequis("[\"Java SE\", \"Maven\"]")
                                .dureeTotaleMinutes(600)
                                .niveau("Intermédiaire")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now())
                                .nombreVues(125L)
                                .build();
                coursRepository.save(coursJava);

                // Media pour cours 1
                createMedia(coursJava, "cascade.jpeg", MediaType.IMG_JPG, 19548L, true, "Diagramme cascade");
                createMedia(coursJava, "v.jpeg", MediaType.IMG_JPG, 25259L, false, "Modèle en V");
                createMedia(coursJava, "iteration.jpeg", MediaType.IMG_JPG, 37546L, false, "Cycle itératif");

                // ========== COURS 2: Gestion de Projet ==========
                Cours coursGestion = Cours.builder()
                                .administrateur(superAdmin)
                                .instructeur(instructeur2)
                                .categorie(gestionCat)
                                .titre("Gestion de Projet Agile")
                                .slug(SlugUtil.toSlug("Gestion de Projet Agile"))
                                .synopsisCourt("Maîtrisez Scrum et les méthodes agiles.")
                                .descriptionComplete(
                                                "Formation complète aux méthodologies agiles et au management de projet.")
                                .objectifsPedagogiques(
                                                "[\"Comprendre Scrum\", \"Organiser des sprints\", \"Utiliser JIRA\"]")
                                .publicCible("[\"Chefs de projet\", \"Product Owners\"]")
                                .prerequis("[\"Notions de management\"]")
                                .dureeTotaleMinutes(480)
                                .niveau("Débutant")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.HYBRIDE)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(10))
                                .nombreVues(340L)
                                .build();
                coursRepository.save(coursGestion);

                // Media pour cours 2
                createMedia(coursGestion, "projSE.jpeg", MediaType.IMG_JPG, 87315L, true, "Gestion projet SE");
                createMedia(coursGestion, "incremental.jpeg", MediaType.IMG_JPG, 17961L, false, "Modèle incrémental");
                createMedia(coursGestion, "ProjetManagementCC4GI.pdf", MediaType.DOC_PDF, 448303L, false,
                                "Support de cours PDF");
                createMedia(coursGestion, "diagram.pdf", MediaType.DOC_PDF, 24211L, false, "Diagrammes UML");

                // ========== COURS 3: Data Science ==========
                Cours coursData = Cours.builder()
                                .administrateur(adminStd)
                                .instructeur(instructeur2)
                                .categorie(devCat)
                                .titre("Introduction à la Data Science")
                                .slug(SlugUtil.toSlug("Introduction à la Data Science"))
                                .synopsisCourt("Découvrez les bases de l'analyse de données.")
                                .descriptionComplete("Un parcours complet pour débuter en data science avec Python.")
                                .objectifsPedagogiques(
                                                "[\"Python pour la data\", \"Pandas et NumPy\", \"Visualisation\"]")
                                .publicCible("[\"Développeurs\", \"Analystes\"]")
                                .prerequis("[\"Python base\"]")
                                .dureeTotaleMinutes(720)
                                .niveau("Débutant")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(false)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(5))
                                .nombreVues(89L)
                                .build();
                coursRepository.save(coursData);

                // Media pour cours 3
                createMedia(coursData, "image2.jpeg", MediaType.IMG_JPG, 30376L, true, "Illustration data");
                createMedia(coursData, "image3.jpeg", MediaType.IMG_JPG, 22299L, false, "Graphique exemple");
                createMedia(coursData, "image4.jpeg", MediaType.IMG_JPG, 51858L, false, "Dashboard analytique");
                createMedia(coursData, "ProjetManagementCC4GI-2.pdf", MediaType.DOC_PDF, 496389L, false,
                                "Documentation avancée");

                System.out.println("Database seeded successfully with 3 courses and multiple media!");
        }

        private void createMedia(Cours cours, String filename, MediaType type, Long size, boolean isPrimary,
                        String altText) {
                Media media = Media.builder()
                                .cours(cours)
                                .nomFichier(filename)
                                .cheminStockage(filename)
                                .urlPublique(BASE_URL + filename)
                                .type(type)
                                .tailleOctets(size)
                                .estPrincipal(isPrimary)
                                .altText(altText)
                                .build();
                mediaRepository.save(media);
        }
}
