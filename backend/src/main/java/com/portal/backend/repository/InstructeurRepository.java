package com.portal.backend.repository;

import com.portal.backend.entity.Instructeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructeurRepository extends JpaRepository<Instructeur, Long> {
}
