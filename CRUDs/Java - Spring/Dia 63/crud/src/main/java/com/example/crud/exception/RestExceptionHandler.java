package com.example.crud.exception;

import com.example.crud.exception.dto.ErroDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> exception(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroDTO body = addInfo(status, e);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErroDTO> emailJaCadastrado(EmailJaCadastradoException e){
        HttpStatus status = HttpStatus.CONFLICT;
        ErroDTO body = addInfo(status, e);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErroDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroDTO body = addInfo(status, e);
        return ResponseEntity.status(status).body(body);
    }

    private ErroDTO addInfo(HttpStatus status,Exception e){
        return new ErroDTO(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                Instant.now()
        );
    }
}