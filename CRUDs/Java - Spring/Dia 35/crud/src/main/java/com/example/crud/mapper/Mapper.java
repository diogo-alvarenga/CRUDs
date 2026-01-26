package com.example.crud.mapper;

import org.springframework.stereotype.Component;

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;

@Component
public class Mapper {

	public SaidaDTO entidadeParaDTO(Usuario usuario) {
		return SaidaDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public Usuario dtoParaEntidade(EntradaDTO dto) {
		return Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.build();
	}
}
