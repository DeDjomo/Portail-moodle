package com.portal.backend.service;

import com.portal.backend.dto.CategorieCreateRequest;
import com.portal.backend.dto.CategorieDto;
import com.portal.backend.entity.Categorie;
import com.portal.backend.repository.CategorieRepository;
import com.portal.backend.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository repository;

    @Transactional
    public CategorieDto createCategorie(CategorieCreateRequest request) {
        String slug = SlugUtil.toSlug(request.nom());

        Categorie parent = null;
        if (request.parentId() != null) {
            parent = repository.findById(request.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
        }

        Categorie categorie = Categorie.builder()
                .nom(request.nom())
                .slug(slug)
                .description(request.description())
                .parent(parent)
                .build();

        return mapToDto(repository.save(categorie));
    }

    @Transactional
    public CategorieDto updateCategorie(Long id, CategorieCreateRequest request) {
        Categorie categorie = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categorie.setNom(request.nom());
        // Do not update slug automatically to preserve links, or do it if requested.
        // For simplicity, we update it if name changes, but usually bad practice for
        // SEO.
        // Assuming we update it here for consistency with "creation".
        categorie.setSlug(SlugUtil.toSlug(request.nom())); // Careful with existing slugs!

        categorie.setDescription(request.description());

        if (request.parentId() != null) {
            Categorie parent = repository.findById(request.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            categorie.setParent(parent);
        } else {
            categorie.setParent(null);
        }

        return mapToDto(repository.save(categorie));
    }

    @Transactional
    public void deleteCategorie(Long id) {
        repository.deleteById(id);
    }

    public CategorieDto getCategorie(Long id) {
        return repository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<CategorieDto> getAllCategories() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public long countChildCategories(Long parentId) {
        return repository.countByParentId(parentId);
    }

    private CategorieDto mapToDto(Categorie entity) {
        return new CategorieDto(
                entity.getId(),
                entity.getParent() != null ? entity.getParent().getId() : null,
                entity.getParent() != null ? entity.getParent().getNom() : null,
                entity.getNom(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getEstActif(),
                entity.getCreatedAt());
    }
}
