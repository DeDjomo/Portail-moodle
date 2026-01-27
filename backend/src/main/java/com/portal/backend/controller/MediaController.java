package com.portal.backend.controller;

import com.portal.backend.dto.MediaDto;
import com.portal.backend.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Tag(name = "Média", description = "Gestion des fichiers multimédias des cours")
public class MediaController {

    private final MediaService service;

    @PostMapping(value = "/cours/{coursId}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajouter un média à un cours")
    public MediaDto uploadMedia(
            @PathVariable Long coursId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam(required = false) String urlExterne,
            @RequestParam(required = false, defaultValue = "false") Boolean estPrincipal,
            @RequestParam(required = false) String altText) {
        return service.uploadMedia(coursId, file, urlExterne, estPrincipal, altText);
    }

    @GetMapping("/cours/{coursId}/media")
    @Operation(summary = "Lister les médias d'un cours")
    public List<MediaDto> getMediaForCourse(@PathVariable Long coursId) {
        return service.getMediaForCourse(coursId);
    }

    @DeleteMapping("/media/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un média")
    public void deleteMedia(@PathVariable Long id) {
        service.deleteMedia(id);
    }

    @GetMapping("/media")
    @Operation(summary = "Lister tous les médias")
    public List<MediaDto> getAllMedia() {
        return service.getAllMedia();
    }

    @GetMapping("/media/{id}")
    @Operation(summary = "Obtenir un média par ID")
    public MediaDto getMediaById(@PathVariable Long id) {
        return service.getMediaById(id);
    }

    @PutMapping("/media/{id}")
    @Operation(summary = "Modifier un média (altText, estPrincipal)")
    public MediaDto updateMedia(
            @PathVariable Long id,
            @RequestParam(required = false) String altText,
            @RequestParam(required = false) Boolean estPrincipal) {
        return service.updateMedia(id, altText, estPrincipal);
    }
}
