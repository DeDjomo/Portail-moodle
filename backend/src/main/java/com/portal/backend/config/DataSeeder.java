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
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

        private final AdministrateurRepository adminRepository;
        private final InstructeurRepository instructeurRepository;
        private final CategorieRepository categorieRepository;
        private final CoursRepository coursRepository;
        private final MediaRepository mediaRepository;
        private final EtudiantRepository etudiantRepository;

        private static final String BASE_URL = "http://localhost:9080/api/uploads/";

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                if (adminRepository.count() == 0) {
                        seedData();
                }
        }

        private void seedData() {
                // ========== ADMINS ==========
                Administrateur superAdmin = Administrateur.builder()
                                .nom("Admin")
                                .prenom("Super")
                                .email("superadmin@portal.com")
                                .telephone("+237 699 123 456")
                                .passwordHash(PasswordUtil.hashPassword("supersecret"))
                                .type(AdminType.SUPER_ADMIN)
                                .statut(AdminStatus.ACTIF)
                                .avatarUrl("https://ui-avatars.com/api/?name=Super+Admin&background=0D8ABC&color=fff")
                                .build();
                adminRepository.save(superAdmin);

                Administrateur adminJohn = Administrateur.builder()
                                .nom("Doe")
                                .prenom("John")
                                .email("johndoe@portal.com")
                                .telephone("+237 677 234 567")
                                .passwordHash(PasswordUtil.hashPassword("password"))
                                .type(AdminType.ADMIN_STANDARD)
                                .statut(AdminStatus.ACTIF)
                                .avatarUrl("https://ui-avatars.com/api/?name=John+Doe&background=0D8ABC&color=fff")
                                .build();
                adminRepository.save(adminJohn);

                Administrateur adminMarie = Administrateur.builder()
                                .nom("Nguemo")
                                .prenom("Marie")
                                .email("marie.nguemo@portal.com")
                                .telephone("+237 655 345 678")
                                .passwordHash(PasswordUtil.hashPassword("password"))
                                .type(AdminType.ADMIN_STANDARD)
                                .statut(AdminStatus.ACTIF)
                                .avatarUrl("https://ui-avatars.com/api/?name=Marie+Nguemo&background=0D8ABC&color=fff")
                                .build();
                adminRepository.save(adminMarie);

                Administrateur adminPaul = Administrateur.builder()
                                .nom("Tchatat")
                                .prenom("Paul")
                                .email("paul.tchatat@portal.com")
                                .telephone("+237 690 456 789")
                                .passwordHash(PasswordUtil.hashPassword("password"))
                                .type(AdminType.ADMIN_STANDARD)
                                .statut(AdminStatus.SUSPENDU)
                                .avatarUrl("https://ui-avatars.com/api/?name=Paul+Tchatat&background=0D8ABC&color=fff")
                                .build();
                adminRepository.save(adminPaul);

                Administrateur adminSophie = Administrateur.builder()
                                .nom("Kamga")
                                .prenom("Sophie")
                                .email("sophie.kamga@portal.com")
                                .telephone("+237 678 567 890")
                                .passwordHash(PasswordUtil.hashPassword("password"))
                                .type(AdminType.ADMIN_STANDARD)
                                .statut(AdminStatus.ACTIF)
                                .avatarUrl("https://ui-avatars.com/api/?name=Sophie+Kamga&background=0D8ABC&color=fff")
                                .build();
                adminRepository.save(adminSophie);

                // ========== INSTRUCTEURS ==========
                Instructeur instProf = Instructeur.builder()
                                .nomComplet("Professeur X")
                                .titreProfessionnel("Expert Java")
                                .organisation("University of Code")
                                .biographieCourte("Passionné par le code propre.")
                                .biographieComplete("Une longue carrière dans le développement logiciel...")
                                .photoUrl("https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400&h=400&fit=crop")
                                .siteWeb("https://professeur-x.dev")
                                .linkedinUrl("https://linkedin.com/in/professeur-x")
                                .build();
                instructeurRepository.save(instProf);

                Instructeur instMarie = Instructeur.builder()
                                .nomComplet("Dr. Marie Curie")
                                .titreProfessionnel("Data Scientist")
                                .organisation("AI Labs")
                                .biographieCourte("Experte en Machine Learning.")
                                .biographieComplete("Docteur en sciences des données avec 10 ans d'expérience...")
                                .photoUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=400&h=400&fit=crop")
                                .siteWeb("https://marie-curie.ai")
                                .linkedinUrl("https://linkedin.com/in/marie-curie")
                                .build();
                instructeurRepository.save(instMarie);

                Instructeur instAlbert = Instructeur.builder()
                                .nomComplet("Albert Mbarga")
                                .titreProfessionnel("Architecte Cloud")
                                .organisation("AWS Partner")
                                .biographieCourte("Spécialiste des infrastructures cloud.")
                                .biographieComplete("Certifié AWS Solutions Architect, expert DevOps...")
                                .photoUrl("https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=400&h=400&fit=crop")
                                .siteWeb("https://albert-mbarga.cloud")
                                .linkedinUrl("https://linkedin.com/in/albert-mbarga")
                                .build();
                instructeurRepository.save(instAlbert);

                Instructeur instFatou = Instructeur.builder()
                                .nomComplet("Fatou Diallo")
                                .titreProfessionnel("UX/UI Designer")
                                .organisation("Design Studio Africa")
                                .biographieCourte("Passionnée par le design centré utilisateur.")
                                .biographieComplete("Designer senior avec un portfolio primé...")
                                .photoUrl("https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=400&h=400&fit=crop")
                                .siteWeb("https://fatou-diallo.design")
                                .linkedinUrl("https://linkedin.com/in/fatou-diallo")
                                .build();
                instructeurRepository.save(instFatou);

                Instructeur instOlivier = Instructeur.builder()
                                .nomComplet("Olivier Nkeng")
                                .titreProfessionnel("Expert Cybersécurité")
                                .organisation("SecureNet Cameroun")
                                .biographieCourte("Consultant en sécurité informatique.")
                                .biographieComplete("Certifié CISSP, CEH, expert en pentesting...")
                                .photoUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=400&fit=crop")
                                .siteWeb("https://olivier-nkeng.sec")
                                .linkedinUrl("https://linkedin.com/in/olivier-nkeng")
                                .build();
                instructeurRepository.save(instOlivier);

                // ========== CATEGORIES ==========
                Categorie devCat = Categorie.builder()
                                .nom("Développement")
                                .slug(SlugUtil.toSlug("Développement"))
                                .description("Cours de programmation et développement logiciel")
                                .build();
                categorieRepository.save(devCat);

                Categorie javaCat = Categorie.builder()
                                .nom("Java Spring")
                                .slug(SlugUtil.toSlug("Java Spring"))
                                .parent(devCat)
                                .description("Framework Spring Boot")
                                .build();
                categorieRepository.save(javaCat);

                Categorie webCat = Categorie.builder()
                                .nom("Développement Web")
                                .slug(SlugUtil.toSlug("Développement Web"))
                                .parent(devCat)
                                .description("Frontend et Backend Web")
                                .build();
                categorieRepository.save(webCat);

                Categorie gestionCat = Categorie.builder()
                                .nom("Gestion de Projet")
                                .slug(SlugUtil.toSlug("Gestion de Projet"))
                                .description("Cours de management et gestion de projet")
                                .build();
                categorieRepository.save(gestionCat);

                Categorie dataCat = Categorie.builder()
                                .nom("Data Science")
                                .slug(SlugUtil.toSlug("Data Science"))
                                .description("Analyse de données et Machine Learning")
                                .build();
                categorieRepository.save(dataCat);

                Categorie cloudCat = Categorie.builder()
                                .nom("Cloud Computing")
                                .slug(SlugUtil.toSlug("Cloud Computing"))
                                .description("AWS, Azure, Google Cloud Platform")
                                .build();
                categorieRepository.save(cloudCat);

                Categorie designCat = Categorie.builder()
                                .nom("Design")
                                .slug(SlugUtil.toSlug("Design"))
                                .description("UX/UI Design et graphisme")
                                .build();
                categorieRepository.save(designCat);

                Categorie secuCat = Categorie.builder()
                                .nom("Cybersécurité")
                                .slug(SlugUtil.toSlug("Cybersécurité"))
                                .description("Sécurité informatique et hacking éthique")
                                .build();
                categorieRepository.save(secuCat);

                // ========== COURS ==========
                // Cours 1: Spring Boot (PUBLIE)
                Cours coursJava = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instProf)
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
                                .datePublication(LocalDateTime.now().minusDays(30))
                                .nombreVues(1250L)
                                .metaTitle("Maîtriser Spring Boot - Formation complète Java")
                                .metaDescription(
                                                "Devenez un expert Spring Boot en apprenant à créer des API REST professionnelles de A à Z.")
                                .url("https://moodle.portal.com/course/spring-boot")
                                .build();
                coursRepository.save(coursJava);
                createMediaWithExternalUrl(coursJava,
                                "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&q=80",
                                MediaType.IMG_JPG, true, "Spring Boot Development");

                // Cours 2: Gestion de Projet Agile (PUBLIE)
                Cours coursGestion = Cours.builder()
                                .administrateur(adminSophie)
                                .instructeur(instMarie)
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
                                .nombreVues(3400L)
                                .metaTitle("Gestion de Projet Agile : Maîtrisez Scrum")
                                .metaDescription(
                                                "Apprenez les méthodologies agiles, Scrum et comment gérer des sprints efficacement.")
                                .url("https://moodle.portal.com/course/agile")
                                .build();
                coursRepository.save(coursGestion);
                createMediaWithExternalUrl(coursGestion,
                                "https://images.unsplash.com/photo-1552664730-d307ca884978?w=800&q=80",
                                MediaType.IMG_JPG, true, "Team collaboration agile");

                // Cours 3: Data Science (PUBLIE)
                Cours coursData = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instMarie)
                                .categorie(dataCat)
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
                                .nombreVues(890L)
                                .metaTitle("Introduction à la Data Science avec Python")
                                .metaDescription(
                                                "Un cours complet pour débuter en data science : analyse de données et visualisation.")
                                .url("https://moodle.portal.com/course/data-science")
                                .build();
                coursRepository.save(coursData);
                createMediaWithExternalUrl(coursData,
                                "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&q=80",
                                MediaType.IMG_JPG, true, "Data Science Analytics");

                // Cours 4: AWS Cloud Practitioner (PUBLIE)
                Cours coursAws = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instAlbert)
                                .categorie(cloudCat)
                                .titre("AWS Cloud Practitioner")
                                .slug(SlugUtil.toSlug("AWS Cloud Practitioner"))
                                .synopsisCourt("Préparez la certification AWS Cloud Practitioner.")
                                .descriptionComplete("Formation complète pour réussir l'examen AWS CLF-C01.")
                                .objectifsPedagogiques(
                                                "[\"Comprendre le cloud\", \"Services AWS\", \"Modèles de facturation\"]")
                                .publicCible("[\"Étudiants\", \"Professionnels IT\"]")
                                .prerequis("[\"Aucun\"]")
                                .dureeTotaleMinutes(540)
                                .niveau("Débutant")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(15))
                                .nombreVues(2150L)
                                .metaTitle("Préparation AWS Cloud Practitioner Certification")
                                .metaDescription(
                                                "Réussissez votre examen AWS CLF-C01 avec notre formation complète sur le cloud AWS.")
                                .url("https://moodle.portal.com/course/aws-cloud")
                                .build();
                coursRepository.save(coursAws);
                createMediaWithExternalUrl(coursAws,
                                "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&q=80",
                                MediaType.IMG_JPG, true, "AWS Cloud Infrastructure");

                // Cours 5: UI/UX Design (PUBLIE)
                Cours coursDesign = Cours.builder()
                                .administrateur(adminSophie)
                                .instructeur(instFatou)
                                .categorie(designCat)
                                .titre("Masterclass UI/UX Design")
                                .slug(SlugUtil.toSlug("Masterclass UI UX Design"))
                                .synopsisCourt("Créez des interfaces utilisateur modernes.")
                                .descriptionComplete("Apprenez Figma, les principes UX et le design system.")
                                .objectifsPedagogiques(
                                                "[\"Maîtriser Figma\", \"Créer des prototypes\", \"Design thinking\"]")
                                .publicCible("[\"Designers\", \"Développeurs frontend\"]")
                                .prerequis("[\"Aucun\"]")
                                .dureeTotaleMinutes(660)
                                .niveau("Intermédiaire")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(20))
                                .nombreVues(1890L)
                                .metaTitle("Masterclass UI/UX Design : Devenez Designer")
                                .metaDescription(
                                                "Apprenez les principes du design, Figma et comment créer des interfaces exceptionnelles.")
                                .url("https://moodle.portal.com/course/ui-ux-design")
                                .build();
                coursRepository.save(coursDesign);
                createMediaWithExternalUrl(coursDesign,
                                "https://images.unsplash.com/photo-1561070791-2526d30994b5?w=800&q=80",
                                MediaType.IMG_JPG, true, "UI UX Design Interface");

                // Cours 6: Hacking Éthique (ARCHIVE)
                Cours coursHacking = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instOlivier)
                                .categorie(secuCat)
                                .titre("Hacking Éthique et Pentesting")
                                .slug(SlugUtil.toSlug("Hacking Éthique et Pentesting"))
                                .synopsisCourt("Devenez un hacker éthique certifié.")
                                .descriptionComplete("Apprenez les techniques de pentesting et la sécurité offensive.")
                                .objectifsPedagogiques(
                                                "[\"Kali Linux\", \"Méthodologie d'audit\", \"Outils de pentesting\"]")
                                .publicCible("[\"Professionnels sécurité\", \"Administrateurs système\"]")
                                .prerequis("[\"Linux\", \"Réseaux\"]")
                                .dureeTotaleMinutes(900)
                                .niveau("Avancé")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.ARCHIVE)
                                .datePublication(LocalDateTime.now().minusMonths(6))
                                .nombreVues(4520L)
                                .metaTitle("Hacking Éthique et Pentesting : Niveau Avancé")
                                .metaDescription(
                                                "Apprenez la sécurité offensive, Kali Linux et les techniques d'audit de sécurité.")
                                .url("https://moodle.portal.com/course/hacking-ethique")
                                .build();
                coursRepository.save(coursHacking);
                createMediaWithExternalUrl(coursHacking,
                                "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?w=800&q=80",
                                MediaType.IMG_JPG, true, "Cybersecurity Hacking");

                // Cours 7: React.js Moderne (PUBLIE)
                Cours coursReact = Cours.builder()
                                .administrateur(adminSophie)
                                .instructeur(instProf)
                                .categorie(webCat)
                                .titre("React.js Moderne avec Hooks")
                                .slug(SlugUtil.toSlug("React.js Moderne avec Hooks"))
                                .synopsisCourt("Maîtrisez React.js avec les dernières fonctionnalités.")
                                .descriptionComplete(
                                                "Formation complète React 18 avec Hooks, Context API et Redux Toolkit.")
                                .objectifsPedagogiques("[\"React Hooks\", \"State Management\", \"API Integration\"]")
                                .publicCible("[\"Développeurs JavaScript\", \"Développeurs Web\"]")
                                .prerequis("[\"JavaScript ES6\", \"HTML/CSS\"]")
                                .dureeTotaleMinutes(780)
                                .niveau("Intermédiaire")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(7))
                                .nombreVues(2890L)
                                .metaTitle("React.js Moderne : Hooks, Context API et Redux")
                                .metaDescription(
                                                "Maîtrisez le développement frontend avec React.js et les dernières fonctionnalités de React 18.")
                                .url("https://moodle.portal.com/course/react-moderne")
                                .build();
                coursRepository.save(coursReact);
                createMediaWithExternalUrl(coursReact,
                                "https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=800&q=80",
                                MediaType.IMG_JPG, true, "React.js Web Development");

                // Cours 8: Machine Learning (BROUILLON - ne sera pas visible par SuperAdmin)
                Cours coursML = Cours.builder()
                                .administrateur(adminSophie)
                                .instructeur(instMarie)
                                .categorie(dataCat)
                                .titre("Machine Learning Avancé")
                                .slug(SlugUtil.toSlug("Machine Learning Avancé"))
                                .synopsisCourt("Deep Learning et réseaux de neurones.")
                                .descriptionComplete(
                                                "Cours avancé sur TensorFlow, PyTorch et les architectures CNN/RNN.")
                                .objectifsPedagogiques("[\"Deep Learning\", \"CNN\", \"RNN\", \"Transformers\"]")
                                .publicCible("[\"Data Scientists\", \"ML Engineers\"]")
                                .prerequis("[\"Python\", \"Math\", \"Data Science base\"]")
                                .dureeTotaleMinutes(1200)
                                .niveau("Avancé")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.BROUILLON)
                                .nombreVues(0L)
                                .metaTitle("Machine Learning Avancé et Deep Learning")
                                .metaDescription(
                                                "Plongez dans les réseaux de neurones, CNN, RNN et les architectures Transformers.")
                                .url("https://moodle.portal.com/course/machine-learning-avance")
                                .build();
                coursRepository.save(coursML);
                createMediaWithExternalUrl(coursML,
                                "https://images.unsplash.com/photo-1555255707-c07966088b7b?w=800&q=80",
                                MediaType.IMG_JPG, true, "Machine Learning Neural Network");

                // Cours 9: DevOps & CI/CD (PUBLIE)
                Cours coursDevops = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instAlbert)
                                .categorie(cloudCat)
                                .titre("DevOps & CI/CD Pipeline")
                                .slug(SlugUtil.toSlug("DevOps CI CD Pipeline"))
                                .synopsisCourt("Automatisez vos déploiements avec Docker et Jenkins.")
                                .descriptionComplete(
                                                "Formation DevOps complète: Docker, Kubernetes, Jenkins, GitLab CI.")
                                .objectifsPedagogiques(
                                                "[\"Docker\", \"Kubernetes\", \"CI/CD\", \"Infrastructure as Code\"]")
                                .publicCible("[\"Développeurs\", \"Ops\", \"SRE\"]")
                                .prerequis("[\"Linux\", \"Git\"]")
                                .dureeTotaleMinutes(840)
                                .niveau("Intermédiaire")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.HYBRIDE)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(25))
                                .nombreVues(1670L)
                                .metaTitle("DevOps & CI/CD : Automatisation Totale")
                                .metaDescription(
                                                "Apprenez Kubernetes, Docker et comment mettre en place des pipelines CI/CD robustes.")
                                .url("https://moodle.portal.com/course/devops-cicd")
                                .build();
                coursRepository.save(coursDevops);
                createMediaWithExternalUrl(coursDevops,
                                "https://images.unsplash.com/photo-1667372393119-3d4c48d07fc9?w=800&q=80",
                                MediaType.IMG_JPG, true, "DevOps Docker Kubernetes");

                // Cours 10: Python pour Débutants (ARCHIVE)
                Cours coursPython = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instProf)
                                .categorie(devCat)
                                .titre("Python pour Débutants")
                                .slug(SlugUtil.toSlug("Python pour Débutants"))
                                .synopsisCourt("Apprenez Python de zéro.")
                                .descriptionComplete("Introduction à la programmation avec Python 3.")
                                .objectifsPedagogiques("[\"Syntaxe Python\", \"Structures de données\", \"Fonctions\"]")
                                .publicCible("[\"Débutants\", \"Étudiants\"]")
                                .prerequis("[\"Aucun\"]")
                                .dureeTotaleMinutes(420)
                                .niveau("Débutant")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(false)
                                .statut(CourseStatus.ARCHIVE)
                                .datePublication(LocalDateTime.now().minusMonths(8))
                                .nombreVues(8900L)
                                .metaTitle("Python pour Débutants : Guide Complet")
                                .metaDescription(
                                                "Apprenez la programmation à partir de zéro avec le langage le plus populaire au monde.")
                                .url("https://moodle.portal.com/course/python-debutants")
                                .build();
                coursRepository.save(coursPython);
                createMediaWithExternalUrl(coursPython,
                                "https://images.unsplash.com/photo-1526379879527-8559ecfcaec0?w=800&q=80",
                                MediaType.IMG_JPG, true, "Python Programming");

                // --- Nouveaux Cours pour couvrir toutes les thématiques ---

                // Cours 11: C++ Avancé (Développement)
                Cours coursCpp = Cours.builder()
                                .administrateur(adminSophie)
                                .instructeur(instProf)
                                .categorie(devCat)
                                .titre("C++ Avancé et Programmation Système")
                                .slug(SlugUtil.toSlug("Cpp Avance"))
                                .synopsisCourt("Maîtrisez la programmation système avec C++.")
                                .descriptionComplete(
                                                "Templates, STL, Gestion mémoire avancée et Design Patterns en C++.")
                                .objectifsPedagogiques(
                                                "[\"STL Standard Template Library\", \"Smart Pointers\", \"Multithreading\"]")
                                .publicCible("[\"Développeurs C++\", \"Ingénieurs Système\"]")
                                .prerequis("[\"C++ Base\"]")
                                .dureeTotaleMinutes(600)
                                .niveau("Avancé")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(12))
                                .nombreVues(1100L)
                                .metaTitle("C++ Avancé et Programmation Système Bas Niveau")
                                .metaDescription(
                                                "Maîtrisez la gestion mémoire, la STL et les templates pour le développement système.")
                                .url("https://moodle.portal.com/course/cpp-avance")
                                .build();
                coursRepository.save(coursCpp);
                createMediaWithExternalUrl(coursCpp,
                                "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?w=800&q=80",
                                MediaType.IMG_JPG, true, "C++ Code");

                // Cours 12: Sécurité Réseaux (Cybersécurité)
                Cours coursSecu = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instOlivier)
                                .categorie(secuCat)
                                .titre("Sécurité des Réseaux et Firewalls")
                                .slug(SlugUtil.toSlug("Securite Reseaux"))
                                .synopsisCourt("Protégez les infrastructures réseaux.")
                                .descriptionComplete("Configuration de pare-feu, VPN, IDS/IPS et analyse de trafic.")
                                .objectifsPedagogiques(
                                                "[\"Configurer un firewall\", \"Mettre en place un VPN\", \"Analyser le trafic Wireshark\"]")
                                .publicCible("[\"Admin Réseaux\", \"Ingénieurs Sécurité\"]")
                                .prerequis("[\"TCP/IP\"]")
                                .dureeTotaleMinutes(500)
                                .niveau("Intermédiaire")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.HYBRIDE)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(3))
                                .nombreVues(750L)
                                .metaTitle("Sécurité des Réseaux, Firewalls et VPN")
                                .metaDescription(
                                                "Apprenez à sécuriser les infrastructures réseaux critiques contre les intrusions.")
                                .url("https://moodle.portal.com/course/securite-reseaux")
                                .build();
                coursRepository.save(coursSecu);
                createMediaWithExternalUrl(coursSecu,
                                "https://images.unsplash.com/photo-1614064641938-3bbee52942c7?w=800&q=80",
                                MediaType.IMG_JPG, true, "Network Security Lock");

                // Cours 13: Angular Masterclass (Web)
                Cours coursAngular = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instProf)
                                .categorie(webCat)
                                .titre("Angular : Le Guide Complet")
                                .slug(SlugUtil.toSlug("Angular Guide Complet"))
                                .synopsisCourt("Devenez expert Angular.")
                                .descriptionComplete("Architecture de composants, RxJS, NgRx et déploiement.")
                                .objectifsPedagogiques("[\"Composants Angular\", \"RxJS\", \"State Management\"]")
                                .publicCible("[\"Développeurs Web\"]")
                                .prerequis("[\"TypeScript\", \"HTML/CSS\"]")
                                .dureeTotaleMinutes(800)
                                .niveau("Intermédiaire")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(18))
                                .nombreVues(1950L)
                                .metaTitle("Angular : Le Guide Complet (v17+)")
                                .metaDescription(
                                                "Développement d'applications d'entreprise avec Angular, RxJS et NgRx.")
                                .url("https://moodle.portal.com/course/angular-complet")
                                .build();
                coursRepository.save(coursAngular);
                createMediaWithExternalUrl(coursAngular,
                                "https://images.unsplash.com/photo-1561736778-92e52a7769ef?w=800&q=80",
                                MediaType.IMG_JPG, true, "Angular Code");

                // Cours 14: Big Data Spark (Data)
                Cours coursBigData = Cours.builder()
                                .administrateur(adminSophie)
                                .instructeur(instMarie)
                                .categorie(dataCat)
                                .titre("Big Data Analytics avec Apache Spark")
                                .slug(SlugUtil.toSlug("Big Data Spark"))
                                .synopsisCourt("Traitez des pétaoctets de données.")
                                .descriptionComplete("Traitement distribué, Spark SQL et Streaming.")
                                .objectifsPedagogiques("[\"Hadoop vs Spark\", \"Spark RDD\", \"Dataframes\"]")
                                .publicCible("[\"Data Engineers\", \"Architectes Big Data\"]")
                                .prerequis("[\"Java/Scala/Python\"]")
                                .dureeTotaleMinutes(950)
                                .niveau("Avancé")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(22))
                                .nombreVues(1300L)
                                .metaTitle("Big Data Analytics avec Apache Spark et Hadoop")
                                .metaDescription(
                                                "Apprenez à traiter massivement les données avec Spark SQL et Spark Streaming.")
                                .url("https://moodle.portal.com/course/big-data-spark")
                                .build();
                coursRepository.save(coursBigData);
                createMediaWithExternalUrl(coursBigData,
                                "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800&q=80",
                                MediaType.IMG_JPG, true, "Big Data Nodes");

                // Cours 15: Google Cloud Platform (Cloud)
                Cours coursGcp = Cours.builder()
                                .administrateur(adminMarie)
                                .instructeur(instAlbert)
                                .categorie(cloudCat)
                                .titre("Google Cloud Platform Architect")
                                .slug(SlugUtil.toSlug("GCP Architect"))
                                .synopsisCourt("Architecture cloud sur GCP.")
                                .descriptionComplete("Compute Engine, Kubernetes Engine (GKE) et BigQuery.")
                                .objectifsPedagogiques("[\"GCE\", \"GKE\", \"Cloud Storage\"]")
                                .publicCible("[\"Architectes Cloud\", \"DevOps\"]")
                                .prerequis("[\"Linux\"]")
                                .dureeTotaleMinutes(680)
                                .niveau("Avancé")
                                .langue(CourseLanguage.EN)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(9))
                                .nombreVues(980L)
                                .metaTitle("Google Cloud Platform (GCP) Professional Architect")
                                .metaDescription(
                                                "Certification Google Cloud : GCE, GKE, BigQuery et architecture cloud.")
                                .url("https://moodle.portal.com/course/gcp-architect")
                                .build();
                coursRepository.save(coursGcp);
                createMediaWithExternalUrl(coursGcp,
                                "https://images.unsplash.com/photo-1484417894907-623942c8ee29?w=800&q=80",
                                MediaType.IMG_JPG, true, "Google Cloud Center");

                // Cours 16: Design System (Design)
                Cours coursDesignSystem = Cours.builder()
                                .administrateur(adminSophie)
                                .instructeur(instFatou)
                                .categorie(designCat)
                                .titre("Créer un Design System")
                                .slug(SlugUtil.toSlug("Design System"))
                                .synopsisCourt("Standardisez vos interfaces.")
                                .descriptionComplete("Atomic Design, Tokens et documentation.")
                                .objectifsPedagogiques("[\"Atomic Design\", \"Tokens\", \"Documentation\"]")
                                .publicCible("[\"UI Designers\", \"Frontend Devs\"]")
                                .prerequis("[\"Figma\"]")
                                .dureeTotaleMinutes(420)
                                .niveau("Intermédiaire")
                                .langue(CourseLanguage.FR)
                                .format(CourseFormat.VIDEO)
                                .estCertifiant(true)
                                .statut(CourseStatus.PUBLIE)
                                .datePublication(LocalDateTime.now().minusDays(14))
                                .nombreVues(2400L)
                                .metaTitle("Créer un Design System Évolutif")
                                .metaDescription(
                                                "Méthodologie pour construire des librairies de composants cohérentes et documentées.")
                                .url("https://moodle.portal.com/course/design-system")
                                .build();
                coursRepository.save(coursDesignSystem);
                createMediaWithExternalUrl(coursDesignSystem,
                                "https://images.unsplash.com/photo-1581291518633-83b4ebd1d83e?w=800&q=80",
                                MediaType.IMG_JPG, true, "Design System Components");

                // --- Génération des étudiants et inscriptions ---
                seedStudentsAndEnrollments(List.of(
                                coursJava, coursGestion, coursData, coursAws, coursDesign,
                                coursHacking, coursReact, coursML, coursDevops, coursPython,
                                coursCpp, coursSecu, coursAngular, coursBigData, coursGcp, coursDesignSystem));

                System.out.println("✅ Database seeded successfully!");
                System.out.println("   - 5 Administrateurs");
                System.out.println("   - 5 Instructeurs");
                System.out.println("   - 8 Catégories");
                System.out.println("   - 16 Cours au total (couvrant toutes les thématiques)");
                System.out.println("");
                System.out.println("🔑 IDENTIFIANTS DE CONNEXION :");
                System.out.println("   👉 Admin Standard : johndoe@portal.com / password");
                System.out.println("   👉 Super Admin    : superadmin@portal.com / supersecret");
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

        private void createMediaWithExternalUrl(Cours cours, String externalUrl, MediaType type, boolean isPrimary,
                        String altText) {
                Media media = Media.builder()
                                .cours(cours)
                                .nomFichier("external-image")
                                .urlExterne(externalUrl)
                                .urlPublique(externalUrl)
                                .type(type)
                                .tailleOctets(0L)
                                .estPrincipal(isPrimary)
                                .altText(altText)
                                .build();
                mediaRepository.save(media);
        }

        private void seedStudentsAndEnrollments(List<Cours> allCourses) {
                // Créer 50 étudiants
                List<Etudiant> etudiants = new ArrayList<>();
                String[] prenoms = { "Jean", "Marie", "Pierre", "Sophie", "Paul", "Julie", "Michel", "Laura", "David",
                                "Sarah", "Thomas", "Emma", "Nicolas", "Leah", "Julien", "Alice", "Lucas", "Chloé",
                                "Martin", "Manon", "Kevin", "Camille", "Alex", "Eva", "Maxime", "Lola", "Antoine",
                                "Zoé", "Romain", "Inès" };
                String[] noms = { "Martin", "Bernard", "Thomas", "Petit", "Robert", "Richard", "Durand", "Dubois",
                                "Moreau", "Laurent", "Simon", "Michel", "Lefebvre", "Leroy", "Roux", "David",
                                "Bertrand",
                                "Morel", "Fournier", "Girard", "Bonnet", "Dupont", "Lambert", "Fontaine", "Rousseau",
                                "Vincent", "Muller", "Lefevre", "Faure", "Andre" };

                Random random = new Random();

                for (int i = 0; i < 50; i++) {
                        String prenom = prenoms[random.nextInt(prenoms.length)];
                        String nom = noms[random.nextInt(noms.length)];
                        String uniqueEmail = prenom.toLowerCase() + "." + nom.toLowerCase() + i + random.nextInt(1000)
                                        + "@student.portal.com";

                        Etudiant etudiant = Etudiant.builder()
                                        .prenom(prenom)
                                        .nom(nom)
                                        .email(uniqueEmail)
                                        .telephone("+237 6" + (10000000 + i))
                                        .filiere("Génie Informatique")
                                        .niveau("Niveau " + (3 + random.nextInt(3)))
                                        .passwordHash(PasswordUtil.hashPassword("student" + i))
                                        .build();

                        // Inscrire à 1 à 8 cours aléatoires
                        int nbInscriptions = 1 + random.nextInt(8);
                        List<Cours> shuffledCourses = new ArrayList<>(allCourses);
                        Collections.shuffle(shuffledCourses);

                        for (int j = 0; j < nbInscriptions; j++) {
                                etudiant.getCoursSuivis().add(shuffledCourses.get(j));
                        }

                        etudiants.add(etudiant);
                }
                etudiantRepository.saveAll(etudiants);

                System.out.println("   - " + etudiants.size() + " Étudiants créés et inscrits aux cours");
        }
}
