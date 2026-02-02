package com.portal.backend.dto;

public record InstructeurCreateRequest(
                String nomComplet,
                String titreProfessionnel,
                String organisation,
                String biographieCourte,
                String biographieComplete,
                String photoUrl,
                String siteWeb,
                String linkedinUrl) {
}
