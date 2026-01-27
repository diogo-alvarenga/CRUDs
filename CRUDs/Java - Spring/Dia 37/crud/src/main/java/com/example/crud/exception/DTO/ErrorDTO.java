package com.example.crud.exception.DTO;
import java.time.Instant;

public record ErrorDTO(int status, String error, String message, Instant timeStamp ) {

}
