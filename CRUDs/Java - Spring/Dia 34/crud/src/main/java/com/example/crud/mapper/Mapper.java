package com.example.crud.mapper;

import java.util.List;

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;

public class Mapper {

	public SaidaDTO entidadeParaDTO(Usuario usuario) {
		return SaidaDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public Usuario dtoParaUsuario(EntradaDTO dto) {
		return Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.build();
	}
	
}
