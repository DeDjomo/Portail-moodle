package com.portal.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdminStatsDto {
    private long totalAdmins;
    private long activeAdmins;
    private long suspendedAdmins;
    private long totalCourses;
    private long totalCategories;
    private long totalEtudiants;
    private long totalInstructors;
}
