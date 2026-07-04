package com.example.crud.dto;


import jakarta.validation.constraints.NotBlank;

public record CarroAtualizacaoDTO(@NotBlank String placa,
                                  String email,
                                  String modelo,
                                  String marca,
                                  Integer ano,
                                  String novaPlaca) {
}
