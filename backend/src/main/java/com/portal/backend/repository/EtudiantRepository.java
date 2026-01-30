package com.portal.backend.repository;

import com.portal.backend.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    @Query(value = "SELECT COUNT(DISTINCT e.id) FROM etudiants e " +
            "JOIN etudiant_cours ec ON e.id = ec.etudiant_id " +
            "JOIN cours c ON ec.cours_id = c.id " +
            "WHERE c.administrateur_id = :adminId " +
            "AND MONTH(ec.date_inscription) = MONTH(CURRENT_DATE()) " +
            "AND YEAR(ec.date_inscription) = YEAR(CURRENT_DATE())", nativeQuery = true)
    long countStudentsEnrolledInAdminCoursesThisMonth(@Param("adminId") Long adminId);

    Optional<Etudiant> findByEmail(String email);

    @Query("SELECT e FROM Etudiant e JOIN e.coursSuivis c WHERE c.id = :coursId")
    java.util.List<Etudiant> findAllByCoursId(@Param("coursId") Long coursId);
}
