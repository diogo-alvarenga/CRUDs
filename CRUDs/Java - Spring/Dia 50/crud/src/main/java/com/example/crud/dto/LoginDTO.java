package com.example.crud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDTO(@NotBlank String email, @NotBlank String senha) {

}
