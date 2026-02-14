package com.example.crud.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException{
	public UsuarioNaoAutorizadoException() {
		super("Usuario não autorizado.");
	}
}
