package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Place;

import java.time.LocalDateTime;

public record PlaceDTO(
        Long id,
        String name,
        String description,
        byte[] image,
        LocalDateTime addedAt
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
