package com.example.crud.exception;


public class EmailJaCadastradoException extends RuntimeException{
	public EmailJaCadastradoException() {
		super("Email já cadastrado.");
	}
}
