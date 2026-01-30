package com.portal.backend.repository;

import com.portal.backend.entity.Cours;
import com.portal.backend.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    Optional<Cours> findBySlug(String slug);

    List<Cours> findByStatut(CourseStatus statut);

    List<Cours> findTop5ByAdministrateurIdOrderByNombreVuesDesc(Long administrateurId);

    List<Cours> findAllByOrderByNombreVuesDesc();

    // Statistics
    long countByStatut(CourseStatus statut);

    long countByCategorieId(Long categorieId);

    long countByAdministrateurId(Long administrateurId);

    // Filters for Admin
    List<Cours> findByAdministrateurIdAndStatut(Long administrateurId, CourseStatus statut);

    List<Cours> findByAdministrateurIdAndCategorieId(Long administrateurId, Long categorieId);

    List<Cours> findByAdministrateurIdAndNiveau(Long administrateurId, String niveau);

    List<Cours> findByAdministrateurIdAndEstCertifiant(Long administrateurId, Boolean estCertifiant);

    List<Cours> findByAdministrateurIdAndFormat(Long administrateurId, com.portal.backend.entity.CourseFormat format);

    // Global Filters
    List<Cours> findByCategorieId(Long categorieId);

    List<Cours> findByNiveau(String niveau);

    List<Cours> findByEstCertifiant(Boolean estCertifiant);

    List<Cours> findByFormat(com.portal.backend.entity.CourseFormat format);

    // Delete all courses belonging to an administrator
    void deleteByAdministrateurId(Long administrateurId);

    List<Cours> findByAdministrateurId(Long administrateurId);

    @Modifying
    @Query(value = "DELETE FROM etudiant_cours WHERE cours_id = :coursId", nativeQuery = true)
    void detachStudentsFromCourse(@Param("coursId") Long coursId);
}
