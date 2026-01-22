package com.example.crud.exception;

import java.time.Instant;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.crud.exception.dto.ErrorMessage;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(SenhaIncorretaException.class)
	private ResponseEntity<ErrorMessage> senhaIncorreta(SenhaIncorretaException exception){
		
	    HttpStatus status = HttpStatus.UNAUTHORIZED;		
		ErrorMessage body = criacaoDeMensagem(status, exception);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	private ResponseEntity<ErrorMessage> usuarioNaoEncontrado(EntityNotFoundException exception){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorMessage body = criacaoDeMensagem(status, exception);
		return ResponseEntity.status(status).body(body);
		
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<ErrorMessage> exceptionGenerica(Exception exception){
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorMessage body = criacaoDeMensagem(status, exception);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(NotFoundException.class)
	private ResponseEntity<ErrorMessage> naoEncontrado(NotFoundException exception){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorMessage body = criacaoDeMensagem(status, exception);
		return ResponseEntity.status(status).body(body);
	}
	
	private ErrorMessage criacaoDeMensagem(HttpStatus status, Exception exception) {
		return new ErrorMessage(status.value(),
				status.getReasonPhrase(),
				exception.getMessage(),
				Instant.now());
	}
	
}
