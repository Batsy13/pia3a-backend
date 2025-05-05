package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Lists;

import java.util.stream.Collectors;

public record ListsDTO(
        Long id,
        String name,
        String icon,
        java.util.List<PlaceDTO> place) {

    public ListsDTO(Lists list) {
        this(
                list.getId(),
                list.getName(),
                list.getIcon(),
                list.getPlaces().stream()
                        .map(PlaceDTO::new).collect(Collectors.toList()));
    }
}
