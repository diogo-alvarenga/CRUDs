package com.example.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EntradaDTO( @NotNull String nome, @Email @NotNull String email, @NotNull String senha) {

}
