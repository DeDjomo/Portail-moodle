package com.portal.backend.repository;

import com.portal.backend.entity.Cours;
import com.portal.backend.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours, Long> {
    Optional<Cours> findBySlug(String slug);

    List<Cours> findByStatut(CourseStatus statut);
}
