package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Place;

import java.time.LocalDateTime;

public record CreatedListPlaceDTO(
        String name,
        String description,
        byte[] image,
        LocalDateTime addedAt
) {
    public CreatedListPlaceDTO(Place place){
        this(place.getName(), place.getDescription(), place.getImage(), place.getAddedAt());
    }
}
