package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Lists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.stream.Collectors;

public record ListsDTO(
        @NotNull Long id,
        @NotBlank String name,
        @NotBlank String icon,
        @NotBlank java.util.List<PlaceDTO> places) {

    public ListsDTO(Lists list) {
        this(
                list.getId(),
                list.getName(),
                list.getIcon(),
                list.getPlaces().stream()
                        .map(PlaceDTO::new).collect(Collectors.toList()));
    }
}
