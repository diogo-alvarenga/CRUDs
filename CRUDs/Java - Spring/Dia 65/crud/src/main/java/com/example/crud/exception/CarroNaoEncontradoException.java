package com.example.crud.exception;

public class CarroNaoEncontradoException extends RuntimeException {
    public CarroNaoEncontradoException() {

        super("Carro não encontrado.");
    }
}
