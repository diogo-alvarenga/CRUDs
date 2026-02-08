package com.example.crud.mapper;

import org.springframework.context.annotation.Configuration;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;

@Configuration
public class Mapper {

	public SaidaDTO entidadeParaDto(Usuario usuario) {
		return SaidaDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
	
	public Usuario dtoParaEntidade(EntradaDTO entrada) {
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
