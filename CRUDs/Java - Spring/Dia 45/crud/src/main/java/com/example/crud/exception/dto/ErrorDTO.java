package com.example.crud.exception.dto;

import java.time.Instant;

import lombok.Builder;

@Builder
public record ErrorDTO(int value, String erro, String mensagem, Instant timestamp) {

}
