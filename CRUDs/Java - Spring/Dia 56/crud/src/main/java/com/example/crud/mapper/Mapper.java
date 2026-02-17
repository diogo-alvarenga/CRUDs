package com.example.crud.mapper;

import org.springframework.stereotype.Component;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;

@Component
public class Mapper {

	public SaidaDTO toDto(Usuario usuario) {
		return SaidaDTO.builder()
				.id(usuario.getId())
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.build();
	}
	
	public Usuario toEntity(EntradaDTO entrada) {
		return Usuario.builder()
				.nome(entrada.nome())
				.email(entrada.email())
				.build();
	}
	
	public Usuario atualizar(AtualizacaoDTO atl, Usuario usuario) {
		return Usuario.builder()
				.nome(atl.nome()!=null?atl.nome():usuario.getNome())
				.email(atl.email()!=null?atl.email():usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
}
