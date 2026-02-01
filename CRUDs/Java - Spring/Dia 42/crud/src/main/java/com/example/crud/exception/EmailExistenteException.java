package com.example.crud.exception;

public class EmailExistenteException extends RuntimeException{
	public  EmailExistenteException() {
		super("Email já utilizado.");
	}
	
}
