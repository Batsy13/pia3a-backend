package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Lists;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreatedListRequestDTO(
        @NotBlank String name,
        @NotBlank String icon,
        List<CreatedListPlaceDTO> places
) {
    public CreatedListRequestDTO(Lists list) {
        this(list.getName(), list.getIcon(),
                list.getPlaces().stream()
                        .map(CreatedListPlaceDTO::new)
                        .toList());
    }
}
