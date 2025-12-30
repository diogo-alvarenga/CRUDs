package com.example.crud2.business;

import org.springframework.stereotype.Service;

import com.example.crud2.infrastructure.DTO.UsuarioDTO;
import com.example.crud2.infrastructure.entitys.Usuario;
import com.example.crud2.infrastructure.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
		return new UsuarioDTO(usuario.getId(), usuario.getNome());
	}
	
	public void deletarUsuario(Integer id) {
		repository.deleteById(id);
	}
	
	public UsuarioDTO buscarUsuario(Integer id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Usuário não encontrado"));
		return new UsuarioDTO(usuario.getId(), usuario.getNome());
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Integer id) {
		Usuario usuarioEntity = buscarUsuarioPrivate(id);
		
		Usuario usuarioAtualizado = Usuario.builder() 
				.nome(usuario.getNome()!= null? usuario.getNome(): usuarioEntity.getNome())
				.senha(usuario.getSenha()!= null? usuario.getSenha(): usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		
		repository.saveAndFlush(usuarioAtualizado);
		
		return new UsuarioDTO(usuarioEntity.getId(),usuarioAtualizado.getNome());
		
	}
	
	private Usuario buscarUsuarioPrivate(Integer id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Usuário não encontrado"));
		return usuario;
	}
}
