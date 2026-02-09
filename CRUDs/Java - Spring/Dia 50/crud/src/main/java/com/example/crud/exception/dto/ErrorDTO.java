package com.example.crud.exception.dto;

import java.time.Instant;

public record ErrorDTO(int valor, String erro, String mensagem, Instant timestamp) {

}
