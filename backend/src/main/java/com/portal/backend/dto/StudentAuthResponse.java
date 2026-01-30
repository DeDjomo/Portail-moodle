package com.portal.backend.dto;

public record StudentAuthResponse(
    boolean success, 
    StudentData data,
    String error
) {
    public record StudentData(
        EtudiantDto etudiant,
        String token,
        String redirect
    ) {}

    public static StudentAuthResponse success(EtudiantDto etudiant, String token) {
        return new StudentAuthResponse(true, new StudentData(etudiant, token, "/dashboard"), null);
    }

    public static StudentAuthResponse error(String message) {
        return new StudentAuthResponse(false, null, message);
    }
}
