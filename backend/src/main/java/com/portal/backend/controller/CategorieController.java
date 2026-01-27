package com.portal.backend.controller;

import com.portal.backend.dto.CategorieCreateRequest;
import com.portal.backend.dto.CategorieDto;
import com.portal.backend.service.CategorieService;
import com.portal.backend.service.CoursService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Catégories", description = "Gestion des catégories de cours")
public class CategorieController {

    private final CategorieService service;
    private final CoursService coursService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une catégorie")
    public CategorieDto createCategorie(@RequestBody CategorieCreateRequest request) {
        return service.createCategorie(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une catégorie")
    public CategorieDto updateCategorie(@PathVariable Long id, @RequestBody CategorieCreateRequest request) {
        return service.updateCategorie(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer une catégorie")
    public void deleteCategorie(@PathVariable Long id) {
        service.deleteCategorie(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une catégorie par ID")
    public CategorieDto getCategorie(@PathVariable Long id) {
        return service.getCategorie(id);
    }

    @GetMapping
    @Operation(summary = "Lister toutes les catégories")
    public List<CategorieDto> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("/{id}/children/count")
    @Operation(summary = "Nombre de sous-catégories")
    public long countChildCategories(@PathVariable Long id) {
        return service.countChildCategories(id);
    }

    @GetMapping("/{id}/cours/count")
    @Operation(summary = "Nombre de cours dans une catégorie")
    public long countCoursByCategory(@PathVariable Long id) {
        return coursService.countCoursByCategory(id);
    }
}
