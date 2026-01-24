package com.example.crud.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.crud.exception.DTO.ErrorDTO;

@RestControllerAdvice
public class RestexceptionHandler {

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	private ResponseEntity<ErrorDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException exception){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		ErrorDTO body = adicionarInformacoes(status, exception);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(SenhaIncorretaException.class)
	private ResponseEntity<ErrorDTO> senhaIncorreta(SenhaIncorretaException exception){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		ErrorDTO body = adicionarInformacoes(status, exception);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<ErrorDTO> exceptionGenerica(Exception exception){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		ErrorDTO body = adicionarInformacoes(status, exception);
		
		return ResponseEntity.status(status).body(body);
	}
	
	private ErrorDTO adicionarInformacoes(HttpStatus status, Exception exception) {
		return new ErrorDTO(status.value(),
				status.getReasonPhrase(),
				exception.getMessage(),
				Instant.now());
	}
}
