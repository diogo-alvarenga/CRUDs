package com.example.crud.exception;

public class TokenInvalidoException extends RuntimeException{
	public TokenInvalidoException() {
		super("Token invalido.");
	}
}
