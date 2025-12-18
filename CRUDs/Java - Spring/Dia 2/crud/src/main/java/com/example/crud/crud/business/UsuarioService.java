package com.example.crud.crud.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.crud.infrastructure.entitys.Usuario;
import com.example.crud.crud.infrastructure.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	
	public void salvarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		//orElseThrow desempacota o optional, e retorna ele ou a exception
		return repository.findByEmail(email).orElseThrow(
				() -> new RuntimeException("O email nao foi encontrado") );
	}
	
	public void deletarUsuarioPorEmail(String email){
		repository.deleteByEmail(email);
	}
	
	public void atualizarUsuarioPorEmail(String email, Usuario usuario) {
		Usuario usuarioEntity = buscarUsuarioPorEmail(email); //esse metodo tem uma lambda
		Usuario usuarioAtualizado = usuario.builder()
				.email(usuario.getEmail() != null? usuario.getEmail() : usuarioEntity.getEmail())
				.nome(usuario.getNome() != null? usuario.getNome() : usuarioEntity.getNome())
				.id(usuarioEntity.getId())
				.build();
		
		repository.saveAndFlush(usuarioAtualizado);
	}
	
	public void atualizarUsuarioPorId(Integer id, Usuario usuario) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario não encontrado"));
		Usuario usuarioAtualizado = usuario.builder()
				.email(usuario.getEmail() != null? usuario.getEmail() : usuarioEntity.getEmail())
				.nome(usuario.getNome() != null? usuario.getNome() : usuarioEntity.getNome())
				.id(usuarioEntity.getId())
				.build();
		
		repository.saveAndFlush(usuarioAtualizado);
	}
}
