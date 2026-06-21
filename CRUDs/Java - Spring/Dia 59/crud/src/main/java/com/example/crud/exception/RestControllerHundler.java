package com.example.crud.exception;

import com.example.crud.exception.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestControllerHundler {

    @ExceptionHandler(EmailCadastradoException.class)
    public ResponseEntity<ErrorDTO> emailCadastrado(EmailCadastradoException e){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorDTO body = addInfo(status,e);

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(CarroInexistenteException.class)
    public ResponseEntity<ErrorDTO> carroInexistente(CarroInexistenteException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDTO body = addInfo(status,e);

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorDTO> usuarioNaoEncontrado(UsuarioNaoEncontradoException e){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDTO body = addInfo(status,e);

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> exception(Exception e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDTO body = addInfo(status, e);

        return ResponseEntity.status(status).body(body);
    }

    private ErrorDTO addInfo(HttpStatus status, Exception e){
        return new ErrorDTO(
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                Instant.now()
        );
    }

}
