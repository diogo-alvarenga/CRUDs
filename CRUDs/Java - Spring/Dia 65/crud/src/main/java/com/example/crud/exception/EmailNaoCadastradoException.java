package com.example.crud.exception;

public class EmailNaoCadastradoException extends RuntimeException {
    public EmailNaoCadastradoException() {
        super("Email não cadastrado.");
    }
}
