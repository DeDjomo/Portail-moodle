package com.portal.backend.dto;

public record AdminCreateRequest(
        String nom,
        String prenom,
        String email,
        String password,
        String telephone) {
}
