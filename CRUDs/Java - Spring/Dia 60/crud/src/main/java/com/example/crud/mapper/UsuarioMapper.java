package com.example.crud.mapper;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.infrastructure.entitys.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {
    public Usuario toEntity(EntradaDTO entradaDTO){
        return Usuario.builder()
                    .email(entradaDTO.email())
                    .nome(entradaDTO.nome())
                .build();
    }

    public SaidaDTO toDTO(Usuario usuario){
        return SaidaDTO.builder()
                    .email(usuario.getEmail())
                    .id(usuario.getId())
                    .nome(usuario.getNome())
                .build();
    }

    public Usuario atualizar(AtualizacaoDTO atl, Usuario usuario){
        return Usuario.builder()
                .id(usuario.getId())
                .email(atl.email()!=null ? atl.email() : usuario.getEmail())
                .nome(atl.nome()!=null ? atl.nome() : usuario.getNome())
                .build();
    }

}
