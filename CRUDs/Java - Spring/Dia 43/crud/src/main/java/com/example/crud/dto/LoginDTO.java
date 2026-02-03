package com.example.crud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginDTO(@NotNull String email, @NotNull String senha) {

}
