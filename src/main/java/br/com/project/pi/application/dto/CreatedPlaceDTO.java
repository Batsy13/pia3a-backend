package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Place;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreatedPlaceDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank String image,
        @NotNull LocalDateTime addedAt
) {
    public CreatedPlaceDTO(Place place){
        this(place.getName(), place.getDescription(), place.getImage(), place.getAddedAt());
    }
}
