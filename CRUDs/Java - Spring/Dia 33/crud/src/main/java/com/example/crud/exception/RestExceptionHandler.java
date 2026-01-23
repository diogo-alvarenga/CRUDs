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
	private ResponseEntity<ErrorResponse> senhaIncorreta(SenhaIncorretaException exception){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorResponse body = criacaoMensagem(status, exception);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	private ResponseEntity<ErrorResponse> usuarioNaoEncontrado(UsuarioNaoEncontradoException exception){
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponse body = criacaoMensagem(status, exception);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<ErrorResponse> excceptionGenerica(Exception exception){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponse body = criacaoMensagem(status,exception);
		
		return ResponseEntity.status(status).body(body);
	}
	
	private ErrorResponse criacaoMensagem(HttpStatus status, Exception exception) {
		return new ErrorResponse(
				status.value(),
				status.getReasonPhrase(),
				exception.getMessage(),
				Instant.now());
	}
}
