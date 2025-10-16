package com.challenge.userpostapi.infrastructure.config;

import com.challenge.userpostapi.domain.enums.RoleEnum;
import com.challenge.userpostapi.domain.model.RoleModel;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.domain.repository.RoleRepositoryInterface;
import com.challenge.userpostapi.domain.repository.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepositoryInterface roleRepository;
    private final UserRepositoryInterface userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) {
        RoleEnum roleEnum = RoleEnum.ADMIN;
        // 1. Crear rol ADMIN
        if (!this.roleRepository.existsByName(roleEnum)) {
            RoleModel roleModel = new RoleModel(roleEnum);
            this.roleRepository.save(roleModel);
            System.out.println("Role inserted successfully");
        }

        // 2. Crear usuario admin si no existe
        if (!this.userRepository.existsByUsername(adminProperties.getUsername())) {
            UserModel admin = new UserModel();
            admin.setUsername(adminProperties.getUsername());
            admin.setPassword(passwordEncoder.encode(adminProperties.getPassword()));
            admin.setRoleModel(this.roleRepository.findByName(roleEnum).orElse(null));
            this.userRepository.save(admin);
            System.out.println("✅ Usuario ADMIN creado por defecto con password desde variable de entorno.");
        }
    }
}
