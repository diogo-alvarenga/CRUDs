package com.example.crud.exception.dto;

import java.time.Instant;

public record ErrorMessage(int status, String error, String message, Instant timesTamp) {
	
}
