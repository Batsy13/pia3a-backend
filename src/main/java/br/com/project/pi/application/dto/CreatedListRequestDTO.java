package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Lists;

import java.util.List;

public record CreatedListRequestDTO(
        String name,
        String icon,
        List<CreatedListPlaceDTO> place
) {
    public CreatedListRequestDTO(Lists list) {
        this(list.getName(), list.getIcon(),
                list.getPlaces().stream()
                        .map(CreatedListPlaceDTO::new)
                        .toList());
    }
}
