package com.portal.backend.controller;

import com.portal.backend.dto.AdminCreateRequest;
import com.portal.backend.dto.AdminDto;
import com.portal.backend.dto.CoursDto;
import com.portal.backend.service.AdminService;
import com.portal.backend.service.CoursService;
import com.portal.backend.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
@Tag(name = "Administrateurs", description = "Gestion des administrateurs")
public class AdminController {

    private final AdminService service;
    private final CoursService coursService;
    private final EtudiantService etudiantService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un administrateur", description = "Crée un nouvel administrateur avec avatar optionnel")
    public AdminDto createAdmin(
            @RequestPart("admin") AdminCreateRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        return service.createAdmin(request, avatar);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Modifier un administrateur")
    public AdminDto updateAdmin(
            @PathVariable Long id,
            @RequestPart("admin") AdminCreateRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        return service.updateAdmin(id, request, avatar);
    }

    @PatchMapping("/{id}/suspend")
    @Operation(summary = "Suspendre un administrateur")
    public void suspendAdmin(@PathVariable Long id) {
        service.suspendAdmin(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un administrateur")
    public void deleteAdmin(@PathVariable Long id) {
        service.deleteAdmin(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un administrateur par ID")
    public AdminDto getAdmin(@PathVariable Long id) {
        return service.getAdmin(id);
    }

    @GetMapping
    @Operation(summary = "Lister tous les administrateurs")
    public List<AdminDto> getAllAdmins() {
        return service.getAllAdmins();
    }

    @GetMapping("/{id}/etudiants/month")
    @Operation(summary = "Nombre d'étudiants inscrits ce mois-ci aux cours de l'admin")
    public long countStudentsEnrolledInAdminCoursesThisMonth(@PathVariable Long id) {
        return etudiantService.countStudentsEnrolledInAdminCoursesThisMonth(id);
    }

    // Admin Specific Course Filters
    @GetMapping("/{id}/cours/statut/{status}")
    @Operation(summary = "Cours de l'admin par statut")
    public List<CoursDto> getAdminCoursByStatus(@PathVariable Long id,
            @PathVariable com.portal.backend.entity.CourseStatus status) {
        return coursService.getAdminCoursByStatus(id, status);
    }

    @GetMapping("/{id}/cours/categorie/{categorieId}")
    @Operation(summary = "Cours de l'admin par catégorie")
    public List<CoursDto> getAdminCoursByCategory(@PathVariable Long id, @PathVariable Long categorieId) {
        return coursService.getAdminCoursByCategory(id, categorieId);
    }

    @GetMapping("/{id}/cours/niveau/{niveau}")
    @Operation(summary = "Cours de l'admin par niveau")
    public List<CoursDto> getAdminCoursByNiveau(@PathVariable Long id, @PathVariable String niveau) {
        return coursService.getAdminCoursByNiveau(id, niveau);
    }

    @GetMapping("/{id}/cours/certifiant/{estCertifiant}")
    @Operation(summary = "Cours de l'admin par certification")
    public List<CoursDto> getAdminCoursByCertifiant(@PathVariable Long id, @PathVariable Boolean estCertifiant) {
        return coursService.getAdminCoursByCertifiant(id, estCertifiant);
    }

    @GetMapping("/{id}/cours/format/{format}")
    @Operation(summary = "Cours de l'admin par format")
    public List<CoursDto> getAdminCoursByFormat(@PathVariable Long id,
            @PathVariable com.portal.backend.entity.CourseFormat format) {
        return coursService.getAdminCoursByFormat(id, format);
    }
}
