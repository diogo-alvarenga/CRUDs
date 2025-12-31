package com.example.crud.business;

import org.springframework.stereotype.Service;

import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.infrastructure.entitys.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	
	public UsuarioService(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		repository.saveAndFlush(usuario);
		return new UsuarioDTO(usuario.getNome(),usuario.getIdade(),usuario.getEmail(),usuario.getEndereco());
	}
	
	public void deletarUsuario(Integer id) {
		repository.deleteById(id);
	}
	
	public UsuarioDTO buscarUsuario(Integer id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario não Encontrado"));
		return new UsuarioDTO(usuarioEntity.getNome(),usuarioEntity.getIdade(),usuarioEntity.getEmail(),usuarioEntity.getEndereco());
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Integer id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuario não Encontrado"));
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome() != null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!= null? usuario.getEmail(): usuarioEntity.getEmail())
				.idade(usuario.getIdade()!= null? usuario.getIdade(): usuarioEntity.getIdade())
				.endereco(usuario.getEndereco() != null? usuario.getEndereco(): usuarioEntity.getEndereco())
				.senha(usuario.getSenha()!= null? usuario.getSenha(): usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		repository.saveAndFlush(usuarioAtualizado);
		return new UsuarioDTO(usuarioAtualizado.getNome(),usuarioAtualizado.getIdade(),usuarioAtualizado.getEmail(),usuarioAtualizado.getEndereco());
	}
}
