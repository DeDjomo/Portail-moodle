package com.portal.backend.dto;

public record CategorieCreateRequest(
        Long parentId,
        String nom,
        String description,
        String iconeClass,
        String couleurHex,
        Integer ordreAffichage) {
}
