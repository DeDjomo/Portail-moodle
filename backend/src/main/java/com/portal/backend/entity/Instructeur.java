package com.portal.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "instructeurs")
public class Instructeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nom_complet", nullable = false)
    private String nomComplet;

    @Column(name = "titre_professionnel", nullable = false)
    private String titreProfessionnel;

    private String organisation;

    @Column(name = "biographie_courte", length = 500)
    private String biographieCourte;

    @Column(name = "biographie_complete", columnDefinition = "TEXT")
    private String biographieComplete;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
