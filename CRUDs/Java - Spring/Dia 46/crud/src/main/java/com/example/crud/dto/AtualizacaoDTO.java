package com.example.crud.dto;

import lombok.Builder;

@Builder
public record AtualizacaoDTO(String email, String senha, String nome) {

}
