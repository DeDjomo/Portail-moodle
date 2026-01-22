package com.portal.backend.service;

import com.portal.backend.dto.AdminCreateRequest;
import com.portal.backend.dto.AdminDto;
import com.portal.backend.entity.AdminStatus;
import com.portal.backend.entity.AdminType;
import com.portal.backend.entity.Administrateur;
import com.portal.backend.repository.AdministrateurRepository;
import com.portal.backend.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdministrateurRepository repository;
    private final FileStorageService fileStorageService;

    @Transactional
    public AdminDto createAdmin(AdminCreateRequest request, MultipartFile avatar) {
        String avatarUrl = null;
        if (avatar != null && !avatar.isEmpty()) {
            String filename = fileStorageService.store(avatar);
            avatarUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();
        }

        Administrateur admin = Administrateur.builder()
                .nom(request.nom())
                .prenom(request.prenom())
                .email(request.email())
                .passwordHash(PasswordUtil.hashPassword(request.password()))
                .telephone(request.telephone())
                .avatarUrl(avatarUrl)
                .type(AdminType.ADMIN_STANDARD)
                .statut(AdminStatus.ACTIF)
                .build();

        return mapToDto(repository.save(admin));
    }

    @Transactional
    public AdminDto updateAdmin(Long id, AdminCreateRequest request, MultipartFile avatar) {
        Administrateur admin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setNom(request.nom());
        admin.setPrenom(request.prenom());
        admin.setEmail(request.email());
        if (request.password() != null && !request.password().isEmpty()) {
            admin.setPasswordHash(PasswordUtil.hashPassword(request.password()));
        }
        admin.setTelephone(request.telephone());

        if (avatar != null && !avatar.isEmpty()) {
            String filename = fileStorageService.store(avatar);
            String avatarUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();
            admin.setAvatarUrl(avatarUrl);
        }

        return mapToDto(repository.save(admin));
    }

    @Transactional
    public void suspendAdmin(Long id) {
        Administrateur admin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        admin.setStatut(AdminStatus.SUSPENDU);
        repository.save(admin);
    }

    @Transactional
    public void deleteAdmin(Long id) {
        repository.deleteById(id);
    }

    public AdminDto getAdmin(Long id) {
        return repository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public List<AdminDto> getAllAdmins() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AdminDto mapToDto(Administrateur entity) {
        return new AdminDto(
                entity.getId(),
                entity.getNom(),
                entity.getPrenom(),
                entity.getEmail(),
                entity.getType(),
                entity.getStatut(),
                entity.getAvatarUrl(),
                entity.getTelephone(),
                entity.getCreatedAt());
    }
}
