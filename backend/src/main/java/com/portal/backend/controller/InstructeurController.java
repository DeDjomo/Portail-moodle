package com.portal.backend.controller;

import com.portal.backend.dto.InstructeurCreateRequest;
import com.portal.backend.dto.InstructeurDto;
import com.portal.backend.service.InstructeurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/instructeurs")
@RequiredArgsConstructor
@Tag(name = "Instructeurs", description = "Gestion des instructeurs")
public class InstructeurController {

    private final InstructeurService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un instructeur")
    public InstructeurDto createInstructeur(
            @RequestPart("instructeur") InstructeurCreateRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        return service.createInstructeur(request, photo);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Modifier un instructeur")
    public InstructeurDto updateInstructeur(
            @PathVariable Long id,
            @RequestPart("instructeur") InstructeurCreateRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        return service.updateInstructeur(id, request, photo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un instructeur")
    public void deleteInstructeur(@PathVariable Long id) {
        service.deleteInstructeur(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un instructeur par ID")
    public InstructeurDto getInstructeur(@PathVariable Long id) {
        return service.getInstructeur(id);
    }

    @GetMapping
    @Operation(summary = "Lister tous les instructeurs")
    public List<InstructeurDto> getAllInstructeurs() {
        return service.getAllInstructeurs();
    }
}
