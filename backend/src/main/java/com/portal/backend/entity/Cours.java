package com.portal.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "administrateur_id", nullable = false)
    private Administrateur administrateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructeur_id", nullable = false)
    private Instructeur instructeur;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "synopsis_court", length = 500)
    private String synopsisCourt;

    @Column(name = "description_complete", columnDefinition = "TEXT")
    private String descriptionComplete;

    // Stored as JSON strings
    @Column(name = "objectifs_pedagogiques", columnDefinition = "JSON")
    private String objectifsPedagogiques;

    @Column(name = "public_cible", columnDefinition = "JSON")
    private String publicCible;

    @Column(name = "prerequis", columnDefinition = "JSON")
    private String prerequis;

    @Column(name = "duree_totale_minutes")
    private Integer dureeTotaleMinutes;

    private String niveau;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CourseLanguage langue = CourseLanguage.FR;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CourseFormat format = CourseFormat.VIDEO;

    @Column(name = "est_certifiant", nullable = false)
    @Builder.Default
    private Boolean estCertifiant = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CourseStatus statut = CourseStatus.BROUILLON;

    @Column(name = "date_publication")
    private LocalDateTime datePublication;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_description", length = 500)
    private String metaDescription;

    @Column(name = "url", length = 500)
    private String url;

    @ManyToMany(mappedBy = "coursSuivis")
    @Builder.Default
    private Set<Etudiant> etudiants = new HashSet<>();

    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private java.util.List<Media> media = new java.util.ArrayList<>();

    @Column(name = "nombre_vues", nullable = false)
    @Builder.Default
    private Long nombreVues = 0L;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
