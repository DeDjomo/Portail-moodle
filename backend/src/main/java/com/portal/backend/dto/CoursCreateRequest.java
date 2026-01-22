package com.portal.backend.dto;

import com.portal.backend.entity.CourseFormat;
import com.portal.backend.entity.CourseLanguage;

public record CoursCreateRequest(
        Long administrateurId,
        Long instructeurId,
        Long categorieId,
        String titre,
        String synopsisCourt,
        String descriptionComplete,
        String objectifsPedagogiques,
        String publicCible,
        String prerequis,
        Integer dureeTotaleMinutes,
        String niveau,
        CourseLanguage langue,
        CourseFormat format,
        Boolean estCertifiant,
        String metaTitle,
        String metaDescription) {
}
