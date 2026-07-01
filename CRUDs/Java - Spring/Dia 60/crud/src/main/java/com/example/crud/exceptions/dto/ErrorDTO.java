package com.example.crud.exceptions.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorDTO(int value, String erro, String mensagem, Instant timestamp) {
}
