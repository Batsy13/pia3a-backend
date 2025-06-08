package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Place;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record PlaceDTO(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank String description,
        @NotNull List<String> images,
        @NotNull LocalDateTime addedAt
) {
    public PlaceDTO(Place place) {
        this(
                place.getId(),
                place.getName(),
                place.getDescription(),
                place.getImages(),
                place.getAddedAt());
    }
}
