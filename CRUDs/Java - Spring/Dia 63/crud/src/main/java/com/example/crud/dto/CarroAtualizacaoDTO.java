package com.example.crud.dto;

import com.example.crud.infrastructure.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CarroAtualizacaoDTO(@NotBlank
                                  String placa,
                                  String modelo,
                                  String marca,
                                  Integer ano,
                                  String email,
                                  String novaPlaca
                                  ) {
}
