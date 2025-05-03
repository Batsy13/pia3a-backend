package br.com.project.pi.application.dto;

public record ListsDTO(
        Long id,
        String name,
        String icon,
        java.util.List<PlaceDTO> place) {

}
