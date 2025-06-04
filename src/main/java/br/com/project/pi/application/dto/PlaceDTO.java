package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Place;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PlaceDTO(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank String description,
        @NotNull byte[] image,
        @NotNull LocalDateTime addedAt
) {
    public PlaceDTO(Place place) {
        this(
                place.getId(),
                place.getName(),
                place.getDescription(),
                place.getImage(),
                place.getAddedAt());
    }
}
