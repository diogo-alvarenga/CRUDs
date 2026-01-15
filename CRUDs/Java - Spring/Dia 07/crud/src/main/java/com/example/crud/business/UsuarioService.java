package com.example.crud.business;

import org.springframework.stereotype.Service;

import com.example.crud.infrastructure.entitys.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public Usuario adicionarUsuario(Usuario usuario) {
		return repository.saveAndFlush(usuario);
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		return repository.findByEmail(email).orElseThrow(
				()-> new RuntimeException("Email não encontrado"));
	}
	
	public void deletarPorId(Integer id) {
		repository.deleteById(id);
	}
	
	public Usuario atualizarUsuarioPorEmail(Usuario usuario, String email) {
		Usuario usuarioEntity = buscarUsuarioPorEmail(email);
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null? usuario.getNome(): usuarioEntity.getNome())
				.email(usuario.getEmail()!= null? usuario.getEmail(): usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
	repository.saveAndFlush(usuarioAtualizado);
	return usuarioAtualizado;
	}
	
	public Usuario atualizarUsuarioPorID(Usuario usuario, Integer id) {
		Usuario usuarioEntity =	repository.findById(id).orElseThrow(
				() -> new RuntimeException("Id não Encontrado"));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null? usuario.getNome(): usuarioEntity.getNome())
				.email(usuario.getEmail()!= null? usuario.getEmail(): usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
	repository.saveAndFlush(usuarioAtualizado);
	return usuarioAtualizado;
	}
}