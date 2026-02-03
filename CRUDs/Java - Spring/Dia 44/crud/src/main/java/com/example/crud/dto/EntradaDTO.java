package com.example.crud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EntradaDTO(@NotBlank String nome, @NotBlank String email, @NotBlank String senha) {

}
