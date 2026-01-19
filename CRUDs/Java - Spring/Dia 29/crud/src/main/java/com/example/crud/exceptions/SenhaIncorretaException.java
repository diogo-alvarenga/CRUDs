package com.example.crud.exceptions;

public class SenhaIncorretaException extends RuntimeException{

	public SenhaIncorretaException() {
		super("Senha incorreta.");
	}
}
