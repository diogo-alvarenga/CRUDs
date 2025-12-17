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
	
	public void salvarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario); // salva e fecha a conexao com o banco
		
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		return repository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("Email não encontrado"));
	}
	
	public void deletarUsuarioPorEmail(String email) {
		repository.deleteByEmail(email);
	}
	
	public void atualizarUsuarioPorEmail(String email, Usuario usuario) {
		Usuario usuarioEntity = buscarUsuarioPorEmail(email);
		
		Usuario usuarioAtualizado = Usuario.builder()
				.email(usuario.getEmail() != null? usuario.getEmail() : usuarioEntity.getEmail())
				.nome(usuario.getNome()!= null? usuario.getNome(): usuarioEntity.getNome())
				.id(usuarioEntity.getId())
				.build();
		
		repository.saveAndFlush(usuarioAtualizado);
	}
	
	public void atualizarUsuarioPorId(Integer id, Usuario usuario) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario não encotrado"));
		
		Usuario usuarioAtualizado = Usuario.builder()
				.email(usuario.getEmail() != null? usuario.getEmail() : usuarioEntity.getEmail())
				.nome(usuario.getNome()!= null? usuario.getNome(): usuarioEntity.getNome())
				.id(usuarioEntity.getId())
				.build();
		
		repository.saveAndFlush(usuarioAtualizado);
	}
}
