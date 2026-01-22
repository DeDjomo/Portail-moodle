package com.portal.backend.dto;

import java.time.LocalDateTime;

public record InstructeurDto(
        Long id,
        String nomComplet,
        String titreProfessionnel,
        String organisation,
        String biographieCourte,
        String biographieComplete,
        String photoUrl,
        String siteWeb,
        String linkedinUrl,
        LocalDateTime createdAt) {
}
