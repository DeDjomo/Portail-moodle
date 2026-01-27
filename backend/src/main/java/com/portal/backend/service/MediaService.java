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
    public MediaDto uploadMedia(Long coursId, MultipartFile file, String urlExterne, Boolean estPrincipal,
            String altText) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (file == null && urlExterne == null) {
            throw new RuntimeException("Either file or external URL must be provided");
        }

        String filename = null;
        String url = null;
        MediaType type = null;
        Long size = null;

        if (file != null) {
            filename = fileStorageService.store(file);
            url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(filename)
                    .toUriString();
            type = determineMediaType(file.getContentType());
            size = file.getSize();
        } else {
            url = null; // No local URL
            // Basic type inference or default
            type = MediaType.VIDEO_MP4; // Default assumption for external links or passed param
            // Ideally we should pass type param for external URL, but for now defaulting
        }

        Media media = Media.builder()
                .cours(cours)
                .nomFichier(filename)
                .cheminStockage(filename)
                .urlPublique(url)
                .urlExterne(urlExterne)
                .type(type)
                .tailleOctets(size)
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
                entity.getUrlExterne(),
                entity.getType(),
                entity.getTailleOctets(),
                entity.getDureeSecondes(),
                entity.getDimensions(),
                entity.getAltText(),
                entity.getEstPrincipal(),
                entity.getCreatedAt());
    }

    public List<MediaDto> getAllMedia() {
        return mediaRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public MediaDto getMediaById(Long id) {
        return mediaRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Media not found"));
    }

    @Transactional
    public MediaDto updateMedia(Long id, String altText, Boolean estPrincipal) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        if (altText != null) {
            media.setAltText(altText);
        }
        if (estPrincipal != null) {
            media.setEstPrincipal(estPrincipal);
        }

        return mapToDto(mediaRepository.save(media));
    }
}
