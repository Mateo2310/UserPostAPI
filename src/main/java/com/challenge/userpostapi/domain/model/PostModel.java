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
public class PostModel {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private UserModel author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
