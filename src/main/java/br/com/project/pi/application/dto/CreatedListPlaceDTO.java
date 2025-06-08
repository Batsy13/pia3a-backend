package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Place;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreatedListPlaceDTO(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull List<String> images,
        @NotNull LocalDateTime addedAt
) {
    public CreatedListPlaceDTO(Place place){
        this(place.getName(), place.getDescription(), place.getImages(), place.getAddedAt());
    }
}
