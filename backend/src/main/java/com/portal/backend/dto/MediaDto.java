package com.portal.backend.dto;

import com.portal.backend.entity.MediaType;
import java.time.LocalDateTime;

public record MediaDto(
        Long id,
        Long coursId,
        String nomFichier,
        String urlPublique,
        MediaType type,
        Long tailleOctets,
        Integer dureeSecondes,
        String dimensions,
        String altText,
        Boolean estPrincipal,
        LocalDateTime createdAt) {
}
