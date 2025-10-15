package com.challenge.userpostapi.infrastructure.controller;

import com.challenge.userpostapi.application.dto.ResponseGeneric;
import com.challenge.userpostapi.application.dto.RoleRequestDTO;
import com.challenge.userpostapi.application.dto.RoleResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.RoleServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceInterface roleService;

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequestDTO roleCreateDTO) {
        this.roleService.save(roleCreateDTO);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Role created", null));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<RoleResponseDTO> roles = this.roleService.findAll();
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Roles", roles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        this.roleService.deleteById(id);
        return ResponseEntity.ok().body(new ResponseGeneric<>("success", "Role deleted succesfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable Long id) {
        RoleResponseDTO role = this.roleService.findById(id);
        return ResponseEntity.ok().body(new  ResponseGeneric<>("success", "Role", role));
    }
}
