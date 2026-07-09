package com.example.crud.exception;

public class EmailJaExistenteException extends RuntimeException {
    public EmailJaExistenteException() {
        super("Email já cadastrado.");
    }
}
