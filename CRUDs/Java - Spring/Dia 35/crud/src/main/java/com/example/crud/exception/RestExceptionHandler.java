package com.example.crud.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.crud.exception.DTO.ErrorResponse;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(SenhaIncorretaException.class)
	private ResponseEntity<ErrorResponse> senhaIncorreta(SenhaIncorretaException e){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		ErrorResponse body = adicionarInformacoes(status, e);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	private ResponseEntity<ErrorResponse> usuarioNaoEncontrado(UsuarioNaoEncontradoException e){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		ErrorResponse body = adicionarInformacoes(status, e);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<ErrorResponse> exceptionGenerica(Exception e){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		ErrorResponse body = adicionarInformacoes(status,e);
		
		return ResponseEntity.status(status).body(body);
		
	}
	
	private ErrorResponse adicionarInformacoes(HttpStatus status, Exception e) {
		return new ErrorResponse(
				status.value(),
				status.getReasonPhrase(),
				e.getMessage(),
				Instant.now()
				);
	}
}
