package com.portal.backend.dto;

import com.portal.backend.entity.AdminStatus;
import com.portal.backend.entity.AdminType;
import java.time.LocalDateTime;

public record AdminDto(
        Long id,
        String nom,
        String prenom,
        String email,
        AdminType type,
        AdminStatus statut,
        String avatarUrl,
        String telephone,
        LocalDateTime createdAt) {
}
