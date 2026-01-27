package com.portal.backend.dto;

import com.portal.backend.entity.CourseFormat;
import com.portal.backend.entity.CourseLanguage;
import com.portal.backend.entity.CourseStatus;
import java.time.LocalDateTime;

public record CoursDto(
                Long id,
                String titre,
                String slug,
                String synopsisCourt,
                String descriptionComplete,
                String objectifsPedagogiques,
                String publicCible,
                String prerequis,
                Integer dureeTotaleMinutes,
                String niveau,
                CourseLanguage langue,
                CourseFormat format,
                Boolean estCertifiant,
                CourseStatus statut,
                LocalDateTime datePublication,
                String metaTitle,
                String metaDescription,
                String url,
                Long nombreVues,
                Long administrateurId,
                Long instructeurId,
                String instructeurNom,
                Long categorieId,
                String categorieNom,
                java.util.List<MediaDto> media,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
