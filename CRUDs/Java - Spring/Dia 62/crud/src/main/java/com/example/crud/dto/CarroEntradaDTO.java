package com.example.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CarroEntradaDTO(@NotBlank String placa, @NotBlank String modelo, @NotBlank String marca, @NotBlank Integer ano, @NotBlank @Email String email) {
}
