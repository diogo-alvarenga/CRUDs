package com.example.crud.business;

import org.springframework.stereotype.Service;

import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;


	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
	
	return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public UsuarioDTO buscarUsuario(Integer id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario nao encontrado"));
		
		return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
	}
	
	public void deletarUsuario(Integer id) {
		repository.deleteById(id);
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Integer id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario nao encontrado"));
	
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!= null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!= null? usuario.getEmail():usuarioEntity.getEmail())
				.senha(usuario.getSenha()!= null? usuario.getSenha():usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
	
		repository.saveAndFlush(usuarioAtualizado);
		
	return new UsuarioDTO(usuarioAtualizado.getId(),usuarioAtualizado.getNome(),usuarioAtualizado.getEmail());
	}
	
}
