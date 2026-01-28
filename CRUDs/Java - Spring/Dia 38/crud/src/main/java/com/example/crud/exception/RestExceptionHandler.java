package com.example.crud.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.crud.exception.DTO.ErrorDTO;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<ErrorDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException e){
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(SenhaIncorretaException.class)
	public ResponseEntity<ErrorDTO> senhaIncorreta(SenhaIncorretaException e){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(EmailJaCadastradoException.class)
	public ResponseEntity<ErrorDTO> EmailJaCadastrado(EmailJaCadastradoException e){
		HttpStatus status = HttpStatus.CONFLICT;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> exceptionGenerica(Exception e){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorDTO body = addInfo(status,e);
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
