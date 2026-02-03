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
				.email(usuario.getEmail())
				.nome(usuario.getNome())
				.id(usuario.getId())
				.build();
	}
	
	public Usuario dtoParaUsuario(EntradaDTO entrada) {
		return Usuario.builder()
				.email(entrada.email())
				.nome(entrada.nome())
				.build();
	}
	
	public Usuario atualizar(AtualizacaoDTO atl, Usuario usuarioEntity) {
		return Usuario.builder()
				.nome(atl.nome()!=null? atl.nome():usuarioEntity.getNome())
				.email(atl.email()!=null?atl.email():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
	}
}
