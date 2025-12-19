package com.example.crud.business;

import org.springframework.stereotype.Service;

import com.example.crud.infrastructure.entitys.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository repository;

	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	
	
	public void salvarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		return repository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("Usuario nao encontrado"));
	}
	
	public void deletarUsuarioPorEmail(String email) {
		repository.deleteByEmail(email);
	}
	
	public void atualizarUsuarioPorEmail(String email, Usuario usuario) {
		Usuario usuarioEntity = buscarUsuarioPorEmail(email);
		
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!= null ? usuario.getNome(): usuarioEntity.getNome() )
				.email(usuario.getEmail()!= null? usuario.getEmail(): usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
	}
	
	public void atualizarUsuarioPorId(Integer id, Usuario usuario) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario não encontrado"));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!= null ? usuario.getNome(): usuarioEntity.getNome() )
				.email(usuario.getEmail()!= null? usuario.getEmail(): usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
	}
	
}
