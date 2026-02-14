package com.example.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record EntradaDTO(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String senha) {

}
