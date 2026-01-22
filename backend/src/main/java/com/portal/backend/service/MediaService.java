package com.portal.backend.service;

import com.portal.backend.dto.MediaDto;
import com.portal.backend.entity.Cours;
import com.portal.backend.entity.Media;
import com.portal.backend.entity.MediaType;
import com.portal.backend.repository.CoursRepository;
import com.portal.backend.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final CoursRepository coursRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public MediaDto uploadMedia(Long coursId, MultipartFile file, Boolean estPrincipal, String altText) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        String filename = fileStorageService.store(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(filename)
                .toUriString();

        MediaType type = determineMediaType(file.getContentType());

        Media media = Media.builder()
                .cours(cours)
                .nomFichier(file.getOriginalFilename())
                .cheminStockage(filename) // storing filename relative to uploads
                .urlPublique(url)
                .type(type)
                .tailleOctets(file.getSize())
                .estPrincipal(estPrincipal != null ? estPrincipal : false)
                .altText(altText)
                .build();

        return mapToDto(mediaRepository.save(media));
    }

    private MediaType determineMediaType(String contentType) {
        if (contentType == null)
            return MediaType.DOC_PDF; // Fallback or throw

        switch (contentType) {
            case "video/mp4":
                return MediaType.VIDEO_MP4;
            case "video/webm":
                return MediaType.VIDEO_WEBM;
            case "image/jpeg":
                return MediaType.IMG_JPG;
            case "image/png":
                return MediaType.IMG_PNG;
            case "application/pdf":
                return MediaType.DOC_PDF;
            default:
                return MediaType.DOC_PDF; // Default or throw
        }
    }

    @Transactional
    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);
        // Could also delete file from storage here
    }

    public List<MediaDto> getMediaForCourse(Long coursId) {
        return mediaRepository.findByCoursId(coursId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private MediaDto mapToDto(Media entity) {
        return new MediaDto(
                entity.getId(),
                entity.getCours().getId(),
                entity.getNomFichier(),
                entity.getUrlPublique(),
                entity.getType(),
                entity.getTailleOctets(),
                entity.getDureeSecondes(),
                entity.getDimensions(),
                entity.getAltText(),
                entity.getEstPrincipal(),
                entity.getCreatedAt());
    }
}
