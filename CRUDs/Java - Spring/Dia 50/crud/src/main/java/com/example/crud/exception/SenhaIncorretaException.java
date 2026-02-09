package com.example.crud.exception;

public class SenhaIncorretaException extends RuntimeException{
	public SenhaIncorretaException() {
		super("Senha incorreta.");
	}
	
}
