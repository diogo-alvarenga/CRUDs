package com.example.crud.dto;

import lombok.Builder;

@Builder
public record AtualizacaoDTO(String nome, String email, String senha) {

}
