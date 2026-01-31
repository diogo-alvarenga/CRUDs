package com.example.crud.exception;

public class EmailExistenteException extends RuntimeException{
	public EmailExistenteException() {
		super("E-Mail já cadastrado.");
	}
}
