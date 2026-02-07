package com.example.crud.business;

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
	private final PasswordEncoder encoder;
	private final Mapper mapper;
	private final JwtUtil jwt;
	
	public SaidaDTO adicionarUsuario(EntradaDTO entrada) {
		if(repository.findByEmail(entrada.email()).isPresent()) throw new EmailCadastradoException();
		
		Usuario usuario = mapper.dtoParaEntidade(entrada);
		usuario.setSenha(encoder.encode(entrada.senha()));
		repository.saveAndFlush(usuario);
		
		return mapper.entidadeParaDTO(usuario);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		return mapper.entidadeParaDTO(buscarPorId(id));
	}
	
	public SaidaDTO atualizarUsuario(Long id, AtualizacaoDTO atl) {
		Usuario usuarioEntity = buscarPorId(id);
		Usuario usuarioAtl = mapper.atualizar(atl, usuarioEntity);
		usuarioAtl.setSenha(atl.senha()!=null?encoder.encode(atl.senha()):usuarioEntity.getSenha());
		repository.saveAndFlush(usuarioAtl);
		
		return mapper.entidadeParaDTO(usuarioAtl);
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) throw new UsuarioNaoEncontradoException();
		repository.deleteById(id);
	}
	
	public List<SaidaDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(mapper::entidadeParaDTO)
				.toList();
	}
	
	public String login(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.email()).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
		if(!encoder.matches(login.senha(), usuario.getSenha())) {
			throw new SenhaIncorretaException();
		}
		
		return jwt.gerarToken(usuario.getNome());
	}
	
	private Usuario buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
	}
	
}
