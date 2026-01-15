package com.example.cruud.business;

import org.springframework.stereotype.Service;

import com.example.cruud.infrastructure.entitys.Usuario;
import com.example.cruud.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public Usuario salvarUsuario(Usuario usuario) {
		return repository.saveAndFlush(usuario);
	}
	
	public Usuario buscarUsuario(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario não Encontrado."));
	}
	
	public void deletarUsuario(Integer id) {
		repository.deleteById(id);
	}
	
	public Usuario atualizarUsuario(Usuario usuario, Integer id) {
		Usuario usuarioEntity = buscarUsuario(id);
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null?usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!=null?usuario.getEmail():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
		
		return repository.saveAndFlush(usuarioAtualizado);
	}
	
	public Usuario atualizarUsuarioPorEmail(Usuario usuario, String email) {
		Usuario usuarioEntity = repository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("Usuario não Encontrado."));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null?usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!=null?usuario.getEmail():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
		
		return repository.saveAndFlush(usuarioAtualizado);
	}
}
