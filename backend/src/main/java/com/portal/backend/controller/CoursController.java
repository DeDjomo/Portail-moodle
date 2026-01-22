package com.portal.backend.controller;

import com.portal.backend.dto.CoursCreateRequest;
import com.portal.backend.dto.CoursDto;
import com.portal.backend.entity.CourseStatus;
import com.portal.backend.service.CoursService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cours")
@RequiredArgsConstructor
@Tag(name = "Cours", description = "Gestion des cours")
public class CoursController {

    private final CoursService service;
    private final com.portal.backend.service.EtudiantService etudiantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un cours")
    public CoursDto createCours(@RequestBody CoursCreateRequest request) {
        return service.createCours(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un cours")
    public CoursDto updateCours(@PathVariable Long id, @RequestBody CoursCreateRequest request) {
        return service.updateCours(id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Changer le statut d'un cours (ex: PUBLIE)")
    public CoursDto changeStatus(@PathVariable Long id, @RequestParam CourseStatus status) {
        return service.changeStatus(id, status);
    }

    @PatchMapping("/{id}/views")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Incrémenter le nombre de vues d'un cours")
    public void incrementViews(@PathVariable Long id) {
        service.incrementViews(id);
    }

    @GetMapping("/{id}/etudiants")
    @Operation(summary = "Lister les étudiants inscrits à un cours")
    public List<com.portal.backend.dto.EtudiantDto> getStudentsForCourse(@PathVariable Long id) {
        // Using EtudiantService would be better, but circular dependency might occur if
        // injected directly.
        // Instead, we can add this method to CoursService or inject EtudiantService
        // lazily.
        // For simplicity and avoiding circular deps in Service layer, we can ask
        // EtudiantService (which depends on CoursService)
        // OR we can move the relationship logic to a specialized service
        // (EnrollmentService).
        // Given EtudiantService already has getStudentsForCourse, let's inject
        // EtudiantService here.
        return etudiantService.getStudentsForCourse(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un cours")
    public void deleteCours(@PathVariable Long id) {
        service.deleteCours(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un cours par ID")
    public CoursDto getCours(@PathVariable Long id) {
        return service.getCours(id);
    }

    @GetMapping
    @Operation(summary = "Lister tous les cours")
    public List<CoursDto> getAllCours() {
        return service.getAllCours();
    }
}
