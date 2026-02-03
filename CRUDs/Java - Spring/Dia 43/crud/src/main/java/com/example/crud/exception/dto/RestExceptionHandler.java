package com.example.crud.exception.dto;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.crud.exception.EmailCadastradoException;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.exception.UsuarioNaoAutorizadoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;

public class RestExceptionHandler {
	@ExceptionHandler(EmailCadastradoException.class)
	public ResponseEntity<ErrorDTO> emailExistente(EmailCadastradoException e){
		HttpStatus status = HttpStatus.CONFLICT;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(SenhaIncorretaException.class)
	public ResponseEntity<ErrorDTO> senhaIncorreta(SenhaIncorretaException e){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<ErrorDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException e){
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(UsuarioNaoAutorizadoException.class)
	public ResponseEntity<ErrorDTO> naoAutorizado(UsuarioNaoAutorizadoException e){
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		ErrorDTO body = addInfo(status, e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> erroDeValidacao(MethodArgumentNotValidException e){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorDTO body = addInfo(status,e);
		return ResponseEntity.status(status).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDTO> exceptionGenerica(Exception e){
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
