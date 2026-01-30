package com.portal.backend.dto;

public record EtudiantCreateRequest(
        String nom,
        String prenom,
        String email,
        String password,
        String filiere,
        String niveau,
        String telephone) {
}
