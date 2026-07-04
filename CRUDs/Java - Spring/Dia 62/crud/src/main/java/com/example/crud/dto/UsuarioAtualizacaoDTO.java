package com.example.crud.dto;

import lombok.Builder;

@Builder
public record UsuarioAtualizacaoDTO(String nome, String email, String senha) {
}
