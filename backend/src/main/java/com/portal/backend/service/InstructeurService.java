package com.portal.backend.service;

import com.portal.backend.dto.InstructeurCreateRequest;
import com.portal.backend.dto.InstructeurDto;
import com.portal.backend.entity.Instructeur;
import com.portal.backend.repository.InstructeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructeurService {

    private final InstructeurRepository repository;
    private final FileStorageService fileStorageService;

    @Transactional
    public InstructeurDto createInstructeur(InstructeurCreateRequest request, MultipartFile photo) {
        String photoUrl = null;
        if (photo != null && !photo.isEmpty()) {
            String filename = fileStorageService.store(photo);
            photoUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();
        }

        Instructeur instructeur = Instructeur.builder()
                .nomComplet(request.nomComplet())
                .titreProfessionnel(request.titreProfessionnel())
                .organisation(request.organisation())
                .biographieCourte(request.biographieCourte())
                .biographieComplete(request.biographieComplete())
                .photoUrl(photoUrl)
                .siteWeb(request.siteWeb())
                .linkedinUrl(request.linkedinUrl())
                .build();

        return mapToDto(repository.save(instructeur));
    }

    @Transactional
    public InstructeurDto updateInstructeur(Long id, InstructeurCreateRequest request, MultipartFile photo) {
        Instructeur instructeur = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructeur not found"));

        instructeur.setNomComplet(request.nomComplet());
        instructeur.setTitreProfessionnel(request.titreProfessionnel());
        instructeur.setOrganisation(request.organisation());
        instructeur.setBiographieCourte(request.biographieCourte());
        instructeur.setBiographieComplete(request.biographieComplete());
        instructeur.setSiteWeb(request.siteWeb());
        instructeur.setLinkedinUrl(request.linkedinUrl());

        if (photo != null && !photo.isEmpty()) {
            String filename = fileStorageService.store(photo);
            String photoUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();
            instructeur.setPhotoUrl(photoUrl);
        }

        return mapToDto(repository.save(instructeur));
    }

    @Transactional
    public void deleteInstructeur(Long id) {
        repository.deleteById(id);
    }

    public InstructeurDto getInstructeur(Long id) {
        return repository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Instructeur not found"));
    }

    public List<InstructeurDto> getAllInstructeurs() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private InstructeurDto mapToDto(Instructeur entity) {
        return new InstructeurDto(
                entity.getId(),
                entity.getNomComplet(),
                entity.getTitreProfessionnel(),
                entity.getOrganisation(),
                entity.getBiographieCourte(),
                entity.getBiographieComplete(),
                entity.getPhotoUrl(),
                entity.getSiteWeb(),
                entity.getLinkedinUrl(),
                entity.getCreatedAt());
    }
}
