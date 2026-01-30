package com.portal.backend.service;

import com.portal.backend.dto.LoginRequest;
import com.portal.backend.dto.LoginResponse;
import com.portal.backend.entity.Administrateur;
import com.portal.backend.repository.AdministrateurRepository;
import com.portal.backend.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdministrateurRepository adminRepository;

    public LoginResponse authenticateAdmin(LoginRequest request) {
        // 1. Chercher l'admin par email
        return adminRepository.findByEmail(request.email())
                .map(admin -> {
                    // 2. Vérifier le mot de passe
                    String hashedInput = PasswordUtil.hashPassword(request.password());
                    if (hashedInput.equals(admin.getPasswordHash())) {

                        // 3. Vérifier si l'admin est suspendu
                        if (admin.getStatut() == com.portal.backend.entity.AdminStatus.SUSPENDU) {
                            return LoginResponse.error("Votre compte est suspendu. Contactez un administrateur.");
                        }

                        // 4. Mettre à jour la dernière connexion
                        admin.setDerniereConnexion(LocalDateTime.now());
                        adminRepository.save(admin);

                        // 5. Générer un token (simulé ici, à remplacer par JWT si besoin)
                        String token = "simulated_token_" + System.currentTimeMillis();

                        return LoginResponse.success(admin, token);
                    } else {
                        return LoginResponse.error("Mot de passe incorrect");
                    }
                })
                .orElse(LoginResponse.error("Utilisateur non trouvé"));
    }
}
