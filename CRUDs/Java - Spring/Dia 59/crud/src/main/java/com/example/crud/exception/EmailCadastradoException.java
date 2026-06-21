package com.example.crud.exception;

public class EmailCadastradoException extends RuntimeException{
    public EmailCadastradoException(){
        super("Email já cadastrado");
    }
}
