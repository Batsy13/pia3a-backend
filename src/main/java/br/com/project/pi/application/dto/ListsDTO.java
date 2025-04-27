package br.com.project.pi.application.dto;

import br.com.project.pi.application.model.Place;

public record ListsDTO(
        String name,
        String icon,
        Place place) {

}
