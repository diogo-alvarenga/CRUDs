package com.example.crud.exception;

public class CarroInexistenteException extends RuntimeException{
    public CarroInexistenteException(){
        super("Carro inexistente.");
    }
}
