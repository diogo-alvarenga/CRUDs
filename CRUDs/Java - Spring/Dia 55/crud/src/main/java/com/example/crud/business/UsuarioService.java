package com.example.crud.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.LoginDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.exception.EmailCadastradoException;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.Mapper;
import com.example.crud.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final Mapper mapper;
	private final PasswordEncoder encoder;
	private final JwtUtil jwt;
	
	public SaidaDTO adicionarUsuario(EntradaDTO entrada) {
		if(repository.findByEmail(entrada.email()).isPresent()) throw new EmailCadastradoException();
		Usuario usuario = mapper.toEntity(entrada);
		usuario.setSenha(encoder.encode(entrada.senha()));
		repository.saveAndFlush(usuario);
		
		return mapper.toDto(usuario);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		return mapper.toDto(buscarPorId(id));
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) throw new UsuarioNaoEncontradoException();
		repository.deleteById(id);
	}
	
	public SaidaDTO atualizarUsuario(Long id, AtualizacaoDTO atl) {
		Usuario usuarioEntity = buscarPorId(id);
		Usuario usuarioAtl = mapper.atualizar(atl,usuarioEntity);
		usuarioAtl.setSenha(atl.senha()!=null?encoder.encode(atl.senha()):usuarioEntity.getSenha());
		repository.saveAndFlush(usuarioAtl);
		
		return mapper.toDto(usuarioAtl);
	}
	
	public List<SaidaDTO> listarUsuarios(){
		return repository.findAll()
				.stream()
				.map(mapper::toDto)
				.toList();
	}
	
	public List<Object> gerarToken(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.email()).orElseThrow(
				() -> new UsuarioNaoEncontradoException());
		if(!encoder.matches(login.senha(), usuario.getSenha())) throw new SenhaIncorretaException();
		
		List<Object> array = new ArrayList<>();
		array.add(jwt.gerarToken(usuario.getNome()));
		array.add("http://localhost:8080/usuario/private");
		
		return array;
	}
	
	private Usuario buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
	}
}
