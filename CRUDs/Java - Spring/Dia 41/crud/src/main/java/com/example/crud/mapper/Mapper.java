package com.example.crud.mapper;

import org.springframework.stereotype.Component;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.infrastructure.entity.Usuario;

@Component
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
				.nome(entrada.getNome())
				.email(entrada.getEmail())
				.build();
	}
	
	public Usuario atualizar(AtualizacaoDTO atl, Usuario usuarioEntity) {
		return Usuario.builder()
				.nome(atl.getNome()!=null?atl.getNome():usuarioEntity.getNome())
				.email(atl.getEmail()!=null?atl.getEmail():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
	}
}
