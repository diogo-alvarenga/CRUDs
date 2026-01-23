package com.example.crud.mapper;

import org.springframework.stereotype.Component;

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;

@Component
public class Mapper {

	public Usuario entradaParaEntidade(EntradaDTO entrada) {
		return Usuario.builder()
				.nome(entrada.getNome())
				.email(entrada.getEmail())
				.build();
	}
	
	public SaidaDTO entidadeParaDTO(Usuario usuario) {
		return SaidaDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
}
