package com.example.crud.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.EntradaIDDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	
	public SaidaDTO adicionarUsuario(EntradaDTO entrada) {
		Usuario usuario = toEntity(entrada);
		repository.saveAndFlush(usuario);
		
		return toSaidaDTO(usuario);
	}
	
	public SaidaDTO atualizarUsuario(EntradaIDDTO entrada) {
		Usuario usuarioEntity = repository.findById(entrada.getId()).orElseThrow(
				()-> new EntityNotFoundException());
		Usuario usuarioAtualizado = Usuario.builder()
				.nome(entrada.getNome()!=null? entrada.getNome():usuarioEntity.getNome())
				.email(entrada.getEmail()!=null?entrada.getEmail():usuarioEntity.getEmail())
				.senha(entrada.getSenha()!=null? encoder.encode(entrada.getSenha()):usuarioEntity.getSenha())
				.id(usuarioEntity.getId())
				.build();
		
		repository.saveAndFlush(usuarioAtualizado);
		
		return toSaidaDTO(usuarioAtualizado);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(
				()-> new EntityNotFoundException());
		return toSaidaDTO(usuario);
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) {
			throw new EntityNotFoundException();
		}
		repository.deleteById(id);
	}
	
	public SaidaDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.getEmail()).orElseThrow(
				()-> new EntityNotFoundException());
		
		if(!encoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return toSaidaDTO(usuario);
	}
	
	public List<SaidaDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(usuario -> SaidaDTO.builder()
						.nome(usuario.getNome())
						.email(usuario.getEmail())
						.id(usuario.getId())
						.build())
				.collect(Collectors.toList());
	}
	
	private Usuario toEntity(EntradaDTO entrada) {
		return Usuario.builder()
				.nome(entrada.getNome())
				.email(entrada.getEmail())
				.senha(encoder.encode(entrada.getSenha()))
				.build();
	}
	
	private SaidaDTO toSaidaDTO(Usuario usuario) {
		return SaidaDTO.builder()
				.nome(usuario.getNome())
				.email(usuario.getEmail())
				.id(usuario.getId())
				.build();
	}
}
