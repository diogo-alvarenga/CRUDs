package com.example.crud.exception.DTO;
import java.time.Instant;
public record ErrorDTO(int value, String error, String message, Instant timestamp) {

}
