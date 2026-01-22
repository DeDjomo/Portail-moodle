package com.portal.backend.repository;

import com.portal.backend.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByCoursId(Long coursId);
}
