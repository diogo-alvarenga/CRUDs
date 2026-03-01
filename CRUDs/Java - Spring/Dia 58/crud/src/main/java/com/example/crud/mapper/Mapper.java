package com.example.crud.mapper;

import org.springframework.stereotype.Component;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;

@Component
public class Mapper {

	public Usuario toEntity(EntradaDTO entrada) {
		return Usuario.builder()
				.email(entrada.email())
				.nome(entrada.nome())
				.build();
	}
	
	public SaidaDTO toDto(Usuario usuario) {
		return SaidaDTO.builder()
				.email(usuario.getEmail())
				.id(usuario.getId())
				.nome(usuario.getNome())
				.build();
	}
	
	public Usuario atualizar(AtualizacaoDTO atl, Usuario usuarioEntity) {
		return Usuario.builder()
				.email(atl.email()!=null? atl.email():usuarioEntity.getEmail())
				.nome(atl.nome()!=null? atl.nome():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
	}
}
