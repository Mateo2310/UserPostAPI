package com.challenge.userpostapi.infrastructure.config;

import com.challenge.userpostapi.domain.model.RoleModel;
import com.challenge.userpostapi.domain.repository.RoleRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepositoryInterface roleRepository;

    @Override
    public void run(String... args) {
        if (!roleRepository.existsByName("ADMIN")) {
            RoleModel adminRole = new RoleModel();
            adminRole.setName("ADMIN");
            this.roleRepository.save(adminRole);
            System.out.println("Rol ADMIN creado.");
        } else {
            System.out.println("Rol ADMIN ya existe");
        }
    }
}
