package com.example.crud.business;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.UsuarioDTO;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;

	public UsuarioDTO adicionarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		repository.saveAndFlush(usuario);
		
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public UsuarioDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario nao encontrado"));
	
		return new UsuarioDTO(usuario.getId(),usuario.getNome(),usuario.getEmail());
	}
	
	public UsuarioDTO atualizarUsuario(Usuario usuario, Long id) {
		Usuario usuarioEntity = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException("Usuario nao encontrado"));
	
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(usuario.getNome()!=null? usuario.getNome():usuarioEntity.getNome())
				.email(usuario.getEmail()!=null? usuario.getEmail():usuarioEntity.getEmail())
				.senha(usuario.getSenha()!=null? encoder.encode(usuario.getSenha()):usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		
		repository.saveAndFlush(usuarioAtualizado);
		
		return new UsuarioDTO(usuarioAtualizado.getId(),usuarioAtualizado.getNome(),usuarioAtualizado.getEmail());
	}
	
	public void deletarUsuario(Long id) {
		repository.deleteById(id);
	}
	
	public List<UsuarioDTO> listarUsuarios(){
		return repository.findAll()//retorna List<Usuario>
				.stream()//converte o List em Stream<Usuario>, stream permite processar um a um
				.map(usuario -> new UsuarioDTO(//transforma cada elemento em DTO, assim virando uma Stream<UsuarioDTO>
						usuario.getId(),
						usuario.getNome(),
						usuario.getEmail()
				))
				.collect(Collectors.toList());//transforma em List novamente
	}
}
