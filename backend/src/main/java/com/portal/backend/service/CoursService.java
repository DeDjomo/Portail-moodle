package com.portal.backend.service;

import com.portal.backend.dto.CoursCreateRequest;
import com.portal.backend.dto.CoursDto;
import com.portal.backend.entity.*;
import com.portal.backend.repository.*;
import com.portal.backend.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoursService {

    private final CoursRepository coursRepository;
    private final AdministrateurRepository adminRepository;
    private final InstructeurRepository instructeurRepository;
    private final CategorieRepository categorieRepository;

    @Transactional
    public CoursDto createCours(CoursCreateRequest request) {
        Administrateur admin = adminRepository.findById(request.administrateurId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        Instructeur instructeur = instructeurRepository.findById(request.instructeurId())
                .orElseThrow(() -> new RuntimeException("Instructeur not found"));
        Categorie categorie = null;
        if (request.categorieId() != null) {
            categorie = categorieRepository.findById(request.categorieId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        String slug = SlugUtil.toSlug(request.titre());

        Cours cours = Cours.builder()
                .administrateur(admin)
                .instructeur(instructeur)
                .categorie(categorie)
                .titre(request.titre())
                .slug(slug)
                .synopsisCourt(request.synopsisCourt())
                .descriptionComplete(request.descriptionComplete())
                .objectifsPedagogiques(request.objectifsPedagogiques())
                .publicCible(request.publicCible())
                .prerequis(request.prerequis())
                .dureeTotaleMinutes(request.dureeTotaleMinutes())
                .niveau(request.niveau())
                .langue(request.langue() != null ? request.langue() : CourseLanguage.FR)
                .format(request.format() != null ? request.format() : CourseFormat.VIDEO)
                .estCertifiant(request.estCertifiant() != null ? request.estCertifiant() : false)
                .metaTitle(request.metaTitle())
                .metaDescription(request.metaDescription())
                .url(request.url())
                .statut(CourseStatus.BROUILLON)
                .build();

        return mapToDto(coursRepository.save(cours));
    }

    @Transactional
    public CoursDto updateCours(Long id, CoursCreateRequest request) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (request.administrateurId() != null) {
            Administrateur admin = adminRepository.findById(request.administrateurId())
                    .orElseThrow(() -> new RuntimeException("Admin not found"));
            cours.setAdministrateur(admin);
        }
        if (request.instructeurId() != null) {
            Instructeur instructeur = instructeurRepository.findById(request.instructeurId())
                    .orElseThrow(() -> new RuntimeException("Instructeur not found"));
            cours.setInstructeur(instructeur);
        }
        if (request.categorieId() != null) {
            Categorie categorie = categorieRepository.findById(request.categorieId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            cours.setCategorie(categorie);
        }

        cours.setTitre(request.titre());
        cours.setSynopsisCourt(request.synopsisCourt());
        cours.setDescriptionComplete(request.descriptionComplete());
        cours.setObjectifsPedagogiques(request.objectifsPedagogiques());
        cours.setPublicCible(request.publicCible());
        cours.setPrerequis(request.prerequis());
        cours.setDureeTotaleMinutes(request.dureeTotaleMinutes());
        cours.setNiveau(request.niveau());
        if (request.langue() != null)
            cours.setLangue(request.langue());
        if (request.format() != null)
            cours.setFormat(request.format());
        if (request.estCertifiant() != null)
            cours.setEstCertifiant(request.estCertifiant());
        cours.setMetaTitle(request.metaTitle());
        cours.setMetaDescription(request.metaDescription());
        cours.setUrl(request.url());

        return mapToDto(coursRepository.save(cours));
    }

    @Transactional
    public CoursDto changeStatus(Long id, CourseStatus newStatus) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        cours.setStatut(newStatus);
        if (newStatus == CourseStatus.PUBLIE) {
            cours.setDatePublication(LocalDateTime.now());
        }

        return mapToDto(coursRepository.save(cours));
    }

    @Transactional
    public void incrementViews(Long id) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        cours.setNombreVues(cours.getNombreVues() + 1);
        coursRepository.save(cours);
    }

    @Transactional
    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }

    public CoursDto getCours(Long id) {
        return coursRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public CoursDto getCoursBySlug(String slug) {
        return coursRepository.findBySlug(slug)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public List<CoursDto> getAllCours() {
        return coursRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Statistics
    public long countAllCours() {
        return coursRepository.count();
    }

    public long countPublishedCours() {
        return coursRepository.countByStatut(CourseStatus.PUBLIE);
    }

    public long countCoursByStatus(CourseStatus status) {
        return coursRepository.countByStatut(status);
    }

    public long countCoursByCategory(Long categoryId) {
        return coursRepository.countByCategorieId(categoryId);
    }

    // Filters for Admin
    public List<CoursDto> getAdminCoursByStatus(Long adminId, CourseStatus status) {
        return coursRepository.findByAdministrateurIdAndStatut(adminId, status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getAdminCoursByCategory(Long adminId, Long categorieId) {
        return coursRepository.findByAdministrateurIdAndCategorieId(adminId, categorieId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getAdminCoursByNiveau(Long adminId, String niveau) {
        return coursRepository.findByAdministrateurIdAndNiveau(adminId, niveau).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getAdminCoursByCertifiant(Long adminId, Boolean estCertifiant) {
        return coursRepository.findByAdministrateurIdAndEstCertifiant(adminId, estCertifiant).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getAdminCoursByFormat(Long adminId, com.portal.backend.entity.CourseFormat format) {
        return coursRepository.findByAdministrateurIdAndFormat(adminId, format).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Global Filters
    public List<CoursDto> getCoursByStatus(CourseStatus status) {
        return coursRepository.findByStatut(status).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getCoursByCategory(Long categorieId) {
        return coursRepository.findByCategorieId(categorieId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getCoursByNiveau(String niveau) {
        return coursRepository.findByNiveau(niveau).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getCoursByCertifiant(Boolean estCertifiant) {
        return coursRepository.findByEstCertifiant(estCertifiant).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getCoursByFormat(com.portal.backend.entity.CourseFormat format) {
        return coursRepository.findByFormat(format).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getTop5CoursesByAdmin(Long adminId) {
        return coursRepository.findTop5ByAdministrateurIdOrderByNombreVuesDesc(adminId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CoursDto> getAllCoursesSortedByViews() {
        return coursRepository.findAllByOrderByNombreVuesDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CoursDto mapToDto(Cours entity) {
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
                entity.getNombreInscritsCalculated() != null ? entity.getNombreInscritsCalculated() : 0,
                entity.getAdministrateur().getId(),
                entity.getInstructeur().getId(),
                entity.getInstructeur().getNomComplet(),
                entity.getCategorie() != null ? entity.getCategorie().getId() : null,
                entity.getCategorie() != null ? entity.getCategorie().getNom() : null,
                entity.getMedia().stream().map(m -> new com.portal.backend.dto.MediaDto(
                        m.getId(), m.getCours().getId(), m.getNomFichier(), m.getUrlPublique(), m.getUrlExterne(),
                        m.getType(), m.getTailleOctets(), m.getDureeSecondes(), m.getDimensions(),
                        m.getAltText(), m.getEstPrincipal(), m.getCreatedAt())).collect(Collectors.toList()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
