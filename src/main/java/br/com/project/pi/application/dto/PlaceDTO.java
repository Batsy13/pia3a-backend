package br.com.project.pi.application.dto;

import java.time.LocalDateTime;

public record PlaceDTO(
        Long id,
        String name,
        String description,
        byte[] image,
        LocalDateTime addedAt
) {
}
