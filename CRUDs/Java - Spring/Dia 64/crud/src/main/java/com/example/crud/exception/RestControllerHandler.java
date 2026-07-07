package com.example.crud.exception;

import com.example.crud.exception.dto.ErrorDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> exception(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDTO body = addInfo(status, e);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorDTO> emailJaCadastrado(EmailJaCadastradoException e){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorDTO body = addInfo(status, e);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDTO body = addInfo(status, e);
        return ResponseEntity.status(status).body(body);
    }

    private ErrorDTO addInfo(HttpStatus status,Exception e){
        return new ErrorDTO(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                Instant.now()
        );
    }
}