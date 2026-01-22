package com.portal.backend.dto;

import java.time.LocalDateTime;

public record EtudiantDto(
        Long id,
        String nom,
        String prenom,
        String email,
        String filiere,
        String niveau,
        String telephone,
        LocalDateTime createdAt) {
}
