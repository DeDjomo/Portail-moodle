package com.portal.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    @Column(name = "nom_fichier")
    private String nomFichier;

    @Column(name = "chemin_stockage", length = 500)
    private String cheminStockage;

    @Column(name = "url_publique", length = 500)
    private String urlPublique;

    @Column(name = "url_externe", length = 500)
    private String urlExterne;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    @Column(name = "taille_octets")
    private Long tailleOctets;

    @Column(name = "duree_secondes")
    private Integer dureeSecondes;

    private String dimensions;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "est_principal", nullable = false)
    @Builder.Default
    private Boolean estPrincipal = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
