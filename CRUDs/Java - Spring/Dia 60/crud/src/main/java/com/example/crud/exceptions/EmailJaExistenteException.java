package com.example.crud.exceptions;

public class EmailJaExistenteException extends RuntimeException{
    public EmailJaExistenteException(){
        super("Email já cadastrado.");
    }
}
