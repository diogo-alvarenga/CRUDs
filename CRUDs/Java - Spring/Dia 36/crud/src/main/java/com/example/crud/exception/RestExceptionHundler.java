package com.example.crud.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.crud.exception.DTO.ErrorDTO;

@RestControllerAdvice
public class RestExceptionHundler {

	@ExceptionHandler(SenhaIncorretaException.class)
	private ResponseEntity<ErrorDTO> senhaIncorreta(SenhaIncorretaException e) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	private ResponseEntity<ErrorDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException e){
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> handleValidation(MethodArgumentNotValidException e) {
	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    ErrorDTO body = addInfo(status, e);
	    return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<ErrorDTO> exception(Exception e){
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
