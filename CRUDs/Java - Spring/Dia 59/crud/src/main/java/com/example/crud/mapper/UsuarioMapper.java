package com.example.crud.mapper;

import com.example.crud.dto.AtualizacaoUsuarioDTO;
import com.example.crud.dto.EntradaUsuarioDTO;
import com.example.crud.dto.SaidaUsuarioDTO;
import com.example.crud.infrastructure.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {

    public SaidaUsuarioDTO entityParaDto(Usuario usuario){
        return SaidaUsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }

    public Usuario dtoParaEntity(EntradaUsuarioDTO entrada){
        return Usuario.builder()
                .nome(entrada.nome())
                .email(entrada.email())
                .build();
    }

    public Usuario atualizarUsuario(AtualizacaoUsuarioDTO atl, Usuario usuarioOriginal){
        return Usuario.builder()
                .nome(atl.nome()!=null? atl.nome() : usuarioOriginal.getNome())
                .email(atl.email()!=null? atl.email() : usuarioOriginal.getEmail())
                .id(usuarioOriginal.getId())
                .build();
    }
}
