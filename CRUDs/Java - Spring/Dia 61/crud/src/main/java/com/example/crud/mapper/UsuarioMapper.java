package com.example.crud.mapper;

import com.example.crud.dto.UsuarioAtualizacaoDTO;
import com.example.crud.dto.UsuarioEntradaDTO;
import com.example.crud.dto.UsuarioSaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {

    public Usuario toEntity(UsuarioEntradaDTO entrada){
        return Usuario.builder()
                .nome(entrada.nome())
                .email(entrada.email())
                .build();
    }

    public UsuarioSaidaDTO toDTO(Usuario usuario){
        return UsuarioSaidaDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }

    public Usuario atualizar(UsuarioAtualizacaoDTO atl, Usuario usuario){
        return Usuario.builder()
                .id(usuario.getId())
                .email(atl.email()!=null ? atl.email() : usuario.getEmail())
                .nome(atl.nome() !=null ? atl.nome(): usuario.getNome())
                .build();
    }
}
