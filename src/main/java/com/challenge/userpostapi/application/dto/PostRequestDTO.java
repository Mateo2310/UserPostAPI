package com.challenge.userpostapi.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDTO {
    @NotBlank(message = "El titulo es obligatorio")
    @NotNull(message = "El titulo es obligatorio")
    private String title;

    @NotBlank(message = "La descripcion es obligatorio")
    @NotNull(message = "La descripcion es obligatorio")
    private String description;
    private String imageUrl;
}
