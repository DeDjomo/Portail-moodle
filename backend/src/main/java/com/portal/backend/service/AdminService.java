package com.portal.backend.service;

import com.portal.backend.dto.AdminCreateRequest;
import com.portal.backend.dto.AdminDto;
import com.portal.backend.entity.AdminStatus;
import com.portal.backend.entity.AdminType;
import com.portal.backend.entity.Administrateur;
import com.portal.backend.repository.AdministrateurRepository;
import com.portal.backend.repository.CoursRepository;
import com.portal.backend.repository.CategorieRepository;
import com.portal.backend.repository.EtudiantRepository;
import com.portal.backend.repository.InstructeurRepository;
import com.portal.backend.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdministrateurRepository repository;
    private final FileStorageService fileStorageService;
    private final CoursRepository coursRepository;
    private final CategorieRepository categorieRepository;
    private final EtudiantRepository etudiantRepository;
    private final InstructeurRepository instructeurRepository;

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
    public AdminDto suspendAdmin(Long id) {
        Administrateur admin = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (admin.getStatut() == AdminStatus.ACTIF) {
            admin.setStatut(AdminStatus.SUSPENDU);
        } else {
            admin.setStatut(AdminStatus.ACTIF);
        }

        return mapToDto(repository.save(admin));
    }

    @Transactional
    public void deleteAdmin(Long id) {
        // Find courses
        List<com.portal.backend.entity.Cours> courses = coursRepository.findByAdministrateurId(id);

        for (com.portal.backend.entity.Cours cours : courses) {
            // Detach students from course directly via SQL to avoid JPA state issues
            coursRepository.detachStudentsFromCourse(cours.getId());

            // Delete course (media will be cascaded)
            coursRepository.delete(cours);
        }

        // Then delete the admin
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

    public com.portal.backend.dto.SuperAdminStatsDto getSuperAdminStats() {
        long totalAdmins = repository.count();
        long activeAdmins = repository.countByStatut(AdminStatus.ACTIF);
        long suspendedAdmins = repository.countByStatut(AdminStatus.SUSPENDU);
        long totalCourses = coursRepository.count();
        long totalCategories = categorieRepository.count();
        long totalEtudiants = etudiantRepository.count();
        long totalInstructors = instructeurRepository.count();

        return com.portal.backend.dto.SuperAdminStatsDto.builder()
                .totalAdmins(totalAdmins)
                .activeAdmins(activeAdmins)
                .suspendedAdmins(suspendedAdmins)
                .totalCourses(totalCourses)
                .totalCategories(totalCategories)
                .totalEtudiants(totalEtudiants)
                .totalInstructors(totalInstructors)
                .build();
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
