package com.challenge.userpostapi.domain.model;

import com.challenge.userpostapi.domain.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel {
    private Long id;
    private RoleEnum name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RoleModel(RoleEnum name) {
        this.name = name;
    }
}
