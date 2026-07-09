package com.example.crud.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CarroAtualizacaoDTO(@NotBlank String placa,
                                  String modelo,
                                  String email,
                                  Integer ano,
                                  Float kmRodados,
                                  String novaPlaca) {
}
