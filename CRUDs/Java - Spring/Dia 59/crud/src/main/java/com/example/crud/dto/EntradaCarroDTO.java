package com.example.crud.dto;

import lombok.Builder;

@Builder
public record EntradaCarroDTO(String modelo,
                              String fabricante,
                              Integer ano,
                              Float kmRodados,
                              Long idUsuario) {
}
