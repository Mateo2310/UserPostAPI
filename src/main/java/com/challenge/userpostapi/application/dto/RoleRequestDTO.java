package com.challenge.userpostapi.application.dto;

import com.challenge.userpostapi.domain.enums.RoleEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDTO {
    @NotNull(message = "El nombre es obligatorio")
    private RoleEnum name;
}
