package com.portal.backend.controller;

import com.portal.backend.dto.CoursDto;
import com.portal.backend.dto.EtudiantCreateRequest;
import com.portal.backend.dto.EtudiantDto;
import com.portal.backend.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
@Tag(name = "Étudiants", description = "Gestion des étudiants et inscriptions")
public class EtudiantController {

    private final EtudiantService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un étudiant")
    public EtudiantDto createEtudiant(@RequestBody EtudiantCreateRequest request) {
        return service.createEtudiant(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un étudiant")
    public EtudiantDto updateEtudiant(@PathVariable Long id, @RequestBody EtudiantCreateRequest request) {
        return service.updateEtudiant(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un étudiant")
    public void deleteEtudiant(@PathVariable Long id) {
        service.deleteEtudiant(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un étudiant par ID")
    public EtudiantDto getEtudiant(@PathVariable Long id) {
        return service.getEtudiant(id);
    }

    @GetMapping
    @Operation(summary = "Lister tous les étudiants")
    public List<EtudiantDto> getAllEtudiants() {
        return service.getAllEtudiants();
    }

    @PostMapping("/{etudiantId}/cours/{coursId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Inscrire un étudiant à un cours")
    public void enrollStudent(@PathVariable Long etudiantId, @PathVariable Long coursId) {
        service.enrollStudent(etudiantId, coursId);
    }

    @GetMapping("/{id}/cours")
    @Operation(summary = "Lister les cours suivis par un étudiant")
    public List<CoursDto> getCoursesForStudent(@PathVariable Long id) {
        return service.getCoursesForStudent(id);
    }
}
