package com.portal.backend.dto;

import java.time.LocalDateTime;

public record CategorieDto(
                Long id,
                Long parentId,
                String parentName,
                String nom,
                String slug,
                String description,
                Boolean estActif,
                LocalDateTime createdAt) {
}
