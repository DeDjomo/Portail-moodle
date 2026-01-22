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
            @RequestPart("file") MultipartFile file,
            @RequestParam(required = false, defaultValue = "false") Boolean estPrincipal,
            @RequestParam(required = false) String altText) {
        return service.uploadMedia(coursId, file, estPrincipal, altText);
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
}
