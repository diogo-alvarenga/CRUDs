package com.example.crud.exception.dto;

import lombok.Builder;

import java.time.Instant;
@Builder
public record ErroDTO(int value, String erro, String mensagem, Instant timestamp) {
}
