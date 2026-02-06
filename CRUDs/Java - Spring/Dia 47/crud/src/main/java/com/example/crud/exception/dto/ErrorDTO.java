package com.example.crud.exception.dto;

import java.time.Instant;

public record ErrorDTO(int value, String erro, String message, Instant timestamp) {

}
