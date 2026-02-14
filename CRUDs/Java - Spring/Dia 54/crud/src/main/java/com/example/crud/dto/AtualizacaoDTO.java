package com.example.crud.dto;

import lombok.Builder;

@Builder
public record AtualizacaoDTO(String nome, String senha, String email) {

}
