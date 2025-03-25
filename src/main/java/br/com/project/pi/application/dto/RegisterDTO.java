package br.com.project.pi.application.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String email,
                          @NotBlank String password) {
}
