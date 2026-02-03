package com.example.crud.business;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.LoginDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.exception.EmailCadastradoException;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.exception.UsuarioNaoAutorizadoException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.jwtutil.JwtUtil;
import com.example.crud.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;
	private final Mapper mapper;
    private final JwtUtil jwtUtil;

	
	public SaidaDTO adicionarUsuario(EntradaDTO entrada) {
		if(repository.findByEmail(entrada.email()).isPresent()) {
			throw new EmailCadastradoException();
		}
		Usuario usuario = mapper.dtoParaUsuario(entrada);
		usuario.setSenha(encoder.encode(entrada.senha()));
		repository.saveAndFlush(usuario);
		
		return mapper.entidadeParaDto(usuario);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		Usuario usuario = buscarUsuarioPorId(id);
		return mapper.entidadeParaDto(usuario);
	}
	
	public SaidaDTO atualizarUsuario(Long id, AtualizacaoDTO atl) {
		Usuario usuarioEntity = buscarUsuarioPorId(id);
		Usuario usuarioAtl = mapper.atualizar(atl, usuarioEntity);
		usuarioAtl.setSenha(atl.senha()!=null? encoder.encode(atl.senha()):usuarioEntity.getSenha());
		repository.saveAndFlush(usuarioAtl);
		
		return mapper.entidadeParaDto(usuarioAtl);
	}
	
	public void deletarPorId(Long id) {
		if(!repository.existsById(id)) {
			throw new UsuarioNaoEncontradoException();
		}
		repository.deleteById(id);
	}
	
	public SaidaDTO login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.email()).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
		if(!encoder.matches(login.senha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return mapper.entidadeParaDto(usuario);
	}
	
	public List<SaidaDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(mapper::entidadeParaDto)
				.toList();
	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		return repository.findById(id).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
	}
	
	public String acessar(String authHeader) {
		if (!authHeader.startsWith("Bearer ")) {
             throw new UsuarioNaoAutorizadoException();
        }
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        
        return "Olá " + username + ", você acessou com JWT";
	}
}
