package com.example.crud.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.crud.exception.dto.ErrorDTO;

@RestControllerAdvice
public class RestControllerHandler {

	@ExceptionHandler(EmailCadastradoException.class)
	public ResponseEntity<ErrorDTO> emailCadastrado(EmailCadastradoException e) {
		HttpStatus status = HttpStatus.CONFLICT;
		ErrorDTO body = addInfo(status, e);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(SenhaIncorretaException.class)
	public ResponseEntity<ErrorDTO> senhaIncorreta(SenhaIncorretaException e) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorDTO body = addInfo(status, e);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(UsuarioNaoAutorizadoException.class)
	public ResponseEntity<ErrorDTO> UsuarioNaoAutorizado(UsuarioNaoAutorizadoException e) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorDTO body = addInfo(status, e);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<ErrorDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException e) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorDTO body = addInfo(status, e);
		
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> exception(Exception e) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorDTO body = addInfo(status, e);
		
		return ResponseEntity.status(status).body(body);
	}
	
	private ErrorDTO addInfo(HttpStatus status, Exception e) {
		return new ErrorDTO(
				status.value(),
				status.getReasonPhrase(),
				e.getMessage(),
				Instant.now()
				);
	}
}
