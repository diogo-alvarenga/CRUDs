package com.example.demo.business;

import org.springframework.stereotype.Service;

import com.example.demo.structure.entity.Usuario;
import com.example.demo.structure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public Usuario adicionarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
		return usuario;
	} 
	
	public Usuario buscarUsuarioPorId(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new RuntimeException("Id não encontrado"));
	}
	
	public void deletarUsuarioPorId(Integer id) {
		repository.deleteById(id);
	}
	
	public Usuario atualizarUsuarioPorId(Usuario usuario, Integer id) {
		Usuario usuarioEntity = buscarUsuarioPorId(id);
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!= null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!= null? usuario.getEmail():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		return usuarioAtualizado;
	}
	
	public Usuario atualizarUsuarioPorEmail(Usuario usuario, String email) {
		Usuario usuarioEntity = repository.findByEmail(email).orElseThrow(
				()-> new RuntimeException("Email não encontrado"));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!= null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!= null? usuario.getEmail():usuarioEntity.getEmail())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		return usuarioAtualizado;
	}
}
