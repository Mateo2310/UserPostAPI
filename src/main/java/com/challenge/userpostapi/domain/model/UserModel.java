package com.challenge.userpostapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private RoleModel roleModel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
