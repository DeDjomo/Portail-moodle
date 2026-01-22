package com.portal.backend.controller;

import com.portal.backend.dto.AdminCreateRequest;
import com.portal.backend.dto.AdminDto;
import com.portal.backend.service.AdminService;
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
}
