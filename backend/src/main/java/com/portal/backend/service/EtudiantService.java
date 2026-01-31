package com.portal.backend.service;

import com.portal.backend.dto.CoursDto;
import com.portal.backend.dto.EtudiantCreateRequest;
import com.portal.backend.dto.EtudiantDto;
import com.portal.backend.entity.Cours;
import com.portal.backend.entity.Etudiant;
import com.portal.backend.repository.CoursRepository;
import com.portal.backend.repository.EtudiantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantService {

        private final EtudiantRepository etudiantRepository;
        private final CoursRepository coursRepository;
        private final EmailService emailService;

        @Transactional
        public EtudiantDto createEtudiant(EtudiantCreateRequest request) {
                Etudiant etudiant = Etudiant.builder()
                                .nom(request.nom())
                                .prenom(request.prenom())
                                .email(request.email())
                                .filiere(request.filiere())
                                .niveau(request.niveau())
                                .telephone(request.telephone())
                                .build();

                return mapToDto(etudiantRepository.save(etudiant));
        }

        @Transactional
        public EtudiantDto updateEtudiant(Long id, EtudiantCreateRequest request) {
                Etudiant etudiant = etudiantRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Etudiant not found"));

                etudiant.setNom(request.nom());
                etudiant.setPrenom(request.prenom());
                etudiant.setEmail(request.email());
                etudiant.setFiliere(request.filiere());
                etudiant.setNiveau(request.niveau());
                etudiant.setTelephone(request.telephone());

                return mapToDto(etudiantRepository.save(etudiant));
        }

        @Transactional
        public void deleteEtudiant(Long id) {
                etudiantRepository.deleteById(id);
        }

        public EtudiantDto getEtudiant(Long id) {
                return etudiantRepository.findById(id)
                                .map(this::mapToDto)
                                .orElseThrow(() -> new RuntimeException("Etudiant not found"));
        }

        public List<EtudiantDto> getAllEtudiants() {
                return etudiantRepository.findAll().stream()
                                .map(this::mapToDto)
                                .collect(Collectors.toList());
        }

        public long countStudentsEnrolledInAdminCoursesThisMonth(Long adminId) {
                return etudiantRepository.countStudentsEnrolledInAdminCoursesThisMonth(adminId);
        }

        @Transactional
        public void enrollStudent(Long etudiantId, Long coursId) {
                Etudiant etudiant = etudiantRepository.findById(etudiantId)
                                .orElseThrow(() -> new RuntimeException("Etudiant not found"));
                Cours cours = coursRepository.findById(coursId)
                                .orElseThrow(() -> new RuntimeException("Cours not found"));

                etudiant.getCoursSuivis().add(cours);
                etudiant.getCoursSuivis().add(cours);
                etudiantRepository.save(etudiant);

                // Send email to course administrator
                try {
                        String adminEmail = cours.getAdministrateur().getEmail();
                        String subject = "Nouvelle inscription au cours : " + cours.getTitre();
                        String message = String.format(
                                        "Bonjour %s,\n\n" +
                                                        "Un nouvel étudiant vient de s'inscrire à votre cours \"%s\".\n\n"
                                                        +
                                                        "Détails de l'étudiant :\n" +
                                                        "Nom : %s\n" +
                                                        "Prénom : %s\n" +
                                                        "Email : %s\n\n" +
                                                        "Cordialement,\n" +
                                                        "L'équipe Portail Moodle",
                                        cours.getAdministrateur().getPrenom(),
                                        cours.getTitre(),
                                        etudiant.getNom(),
                                        etudiant.getPrenom(),
                                        etudiant.getEmail());

                        emailService.sendSimpleMessage(adminEmail, subject, message);
                } catch (Exception e) {
                        // Log error but don't fail enrollment
                        System.err.println("Failed to send enrollment email: " + e.getMessage());
                        e.printStackTrace();
                }

                // Auto-increment view count on enrollment
                cours.setNombreVues(cours.getNombreVues() + 1);
                coursRepository.save(cours);
        }

        public List<EtudiantDto> getStudentsForCourse(Long coursId) {
                Cours cours = coursRepository.findById(coursId)
                                .orElseThrow(() -> new RuntimeException("Cours not found"));
                return cours.getEtudiants().stream()
                                .map(this::mapToDto)
                                .collect(Collectors.toList());
        }

        public List<CoursDto> getCoursesForStudent(Long etudiantId) {
                Etudiant etudiant = etudiantRepository.findById(etudiantId)
                                .orElseThrow(() -> new RuntimeException("Etudiant not found"));

                // We need to manually map Cours to CoursDto here or expose a mapper in
                // CoursService.
                // For simplicity, implementing a basic mapper here or fetching via ID through
                // service if needed.
                // A cleaner way is to use a shared mapper component, but I'll replicate the
                // mapping for now or inject CoursService.
                // Actually, CoursService is injected, let's use it if it has a public mapper...
                // it doesn't.
                // I will implement a private mapper here that matches CoursDto structure.

                return etudiant.getCoursSuivis().stream()
                                .map(this::mapToCoursDto)
                                .collect(Collectors.toList());
        }

        private EtudiantDto mapToDto(Etudiant entity) {
                return new EtudiantDto(
                                entity.getId(),
                                entity.getNom(),
                                entity.getPrenom(),
                                entity.getEmail(),
                                entity.getFiliere(),
                                entity.getNiveau(),
                                entity.getTelephone(),
                                entity.getCreatedAt());
        }

        private CoursDto mapToCoursDto(Cours entity) {
                return new CoursDto(
                                entity.getId(),
                                entity.getTitre(),
                                entity.getSlug(),
                                entity.getSynopsisCourt(),
                                entity.getDescriptionComplete(),
                                entity.getObjectifsPedagogiques(),
                                entity.getPublicCible(),
                                entity.getPrerequis(),
                                entity.getDureeTotaleMinutes(),
                                entity.getNiveau(),
                                entity.getLangue(),
                                entity.getFormat(),
                                entity.getEstCertifiant(),
                                entity.getStatut(),
                                entity.getDatePublication(),
                                entity.getMetaTitle(),
                                entity.getMetaDescription(),
                                entity.getUrl(),
                                entity.getNombreVues(),
                                entity.getAdministrateur().getId(),
                                entity.getInstructeur().getId(),
                                entity.getInstructeur().getNomComplet(),
                                entity.getCategorie() != null ? entity.getCategorie().getId() : null,
                                entity.getCategorie() != null ? entity.getCategorie().getNom() : null,
                                entity.getMedia().stream().map(m -> new com.portal.backend.dto.MediaDto(
                                                m.getId(), m.getCours().getId(), m.getNomFichier(), m.getUrlPublique(),
                                                m.getUrlExterne(),
                                                m.getType(), m.getTailleOctets(), m.getDureeSecondes(),
                                                m.getDimensions(),
                                                m.getAltText(), m.getEstPrincipal(), m.getCreatedAt()))
                                                .collect(Collectors.toList()),
                                entity.getCreatedAt(),
                                entity.getUpdatedAt());
        }
}
