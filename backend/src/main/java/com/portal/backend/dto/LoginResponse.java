package com.portal.backend.dto;

import com.portal.backend.entity.Administrateur;

public record LoginResponse(
    boolean success, 
    AdminData data,
    String error
) {
    public record AdminData(
        Administrateur admin,
        String token,
        String redirect
    ) {}

    public static LoginResponse success(Administrateur admin, String token) {
        String redirect = admin.getType().name().equals("SUPER_ADMIN") ? "/superadmin" : "/dashboard";
        return new LoginResponse(true, new AdminData(admin, token, redirect), null);
    }

    public static LoginResponse error(String message) {
        return new LoginResponse(false, null, message);
    }
}
