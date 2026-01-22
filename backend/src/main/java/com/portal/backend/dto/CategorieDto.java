package com.portal.backend.dto;

import java.time.LocalDateTime;

public record CategorieDto(
        Long id,
        Long parentId,
        String parentName,
        String nom,
        String slug,
        String description,
        String iconeClass,
        String couleurHex,
        Integer ordreAffichage,
        Boolean estActif,
        LocalDateTime createdAt) {
}
