package com.challenge.userpostapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequestDTO {
//    @NotBlank(message = "El nombre es obligatorio")
//    @NotNull(message = "El nombre es obligatorio")
    private String name;
}
