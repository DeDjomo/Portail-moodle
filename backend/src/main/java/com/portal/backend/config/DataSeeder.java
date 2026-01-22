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

                // Instructeur
                Instructeur instructeur = Instructeur.builder()
                                .nomComplet("Professeur X")
                                .titreProfessionnel("Expert Java")
                                .organisation("University of Code")
                                .biographieCourte("Passionné par le code propre.")
                                .biographieComplete("Une longue carrière dans le développement...")
                                .build();
                instructeurRepository.save(instructeur);

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

                // Cours
                Cours coursJava = Cours.builder()
                                .administrateur(adminStd)
                                .instructeur(instructeur)
                                .categorie(javaCat)
                                .titre("Maîtriser Spring Boot")
                                .slug(SlugUtil.toSlug("Maîtriser Spring Boot"))
                                .synopsisCourt("Apprenez à créer des API REST.")
                                .descriptionComplete("Ce cours couvre tout de A à Z...")
                                .objectifsPedagogiques("[\"Comprendre Spring\", \"Créer une API\"]")
                                .publicCible("[\"Développeurs Java\"]")
                                .prerequis("[\"Java Base\"]")
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

                // Media
                Media videoIntro = Media.builder()
                                .cours(coursJava)
                                .nomFichier("intro.mp4")
                                .cheminStockage("intro.mp4")
                                .urlPublique("http://localhost:9080/api/uploads/intro.mp4")
                                .type(MediaType.VIDEO_MP4)
                                .tailleOctets(1024000L)
                                .estPrincipal(true)
                                .build();
                mediaRepository.save(videoIntro);

                System.out.println("Database seeded successfully!");
        }
}
