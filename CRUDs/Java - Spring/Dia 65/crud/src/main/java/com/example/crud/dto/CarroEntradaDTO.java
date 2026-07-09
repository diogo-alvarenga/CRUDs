package com.example.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CarroEntradaDTO(@NotBlank String placa,
                              @NotBlank String modelo,
                              @NotBlank @Email String email,
                              @NotNull Integer ano,
                              @NotNull Float kmRodados) {

}
