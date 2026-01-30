package com.portal.backend.controller;

import com.portal.backend.dto.EtudiantCreateRequest;
import com.portal.backend.dto.EtudiantDto;
import com.portal.backend.dto.LoginRequest;
import com.portal.backend.dto.LoginResponse;
import com.portal.backend.service.AuthService;
import com.portal.backend.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "Gestion de l'authentification")
public class AuthController {

    private final AuthService authService;
    private final EtudiantService etudiantService;

    @PostMapping("/admin/login")
    @Operation(summary = "Connexion Administrateur", description = "Permet à un administrateur (Super ou Standard) de se connecter")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticateAdmin(request);
        
        if (response.success()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Inscription Étudiant", description = "Permet à un étudiant de s'inscrire sur la plateforme")
    public ResponseEntity<EtudiantDto> registerStudent(@RequestBody EtudiantCreateRequest request) {
        try {
            EtudiantDto etudiant = etudiantService.createEtudiant(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(etudiant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
