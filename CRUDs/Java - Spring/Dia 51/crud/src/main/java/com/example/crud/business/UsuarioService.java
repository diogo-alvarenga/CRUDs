package com.example.crud.business;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	public SaidaDTO adicionarUsuario(EntradaDTO entrada) {
		if(repository.findByEmail(entrada.email()).isPresent()) throw new EmailCadastradoException();
		log.info("Criando usuário: {}", entrada.nome());
		Usuario usuario = mapper.dtoParaEntidade(entrada);
		usuario.setSenha(encoder.encode(entrada.senha()));
		repository.saveAndFlush(usuario);
		
		log.info("Usuário criado: {}", entrada.nome());
		return mapper.entidadeParaDto(usuario);
	}
	
	public SaidaDTO buscarUsuario(Long id) {
		return mapper.entidadeParaDto(buscarPorId(id));
	}
	
	public SaidaDTO atualizarUsuario(Long id, AtualizacaoDTO atl) {
		Usuario usuarioEntity = buscarPorId(id);
		log.info("Usuário encontrado: {}", usuarioEntity.getNome());
		Usuario usuarioAtl = mapper.atualizar(atl,usuarioEntity);
		usuarioAtl.setSenha(atl.senha()!=null?encoder.encode(atl.senha()):usuarioEntity.getSenha());
		repository.saveAndFlush(usuarioAtl);
		log.info("Usuário atualizado: {}", usuarioAtl.getNome());

		return mapper.entidadeParaDto(usuarioAtl);
	}
	
	public void deletarUsuario(Long id) {
		if(!repository.existsById(id)) throw new UsuarioNaoEncontradoException();
		repository.deleteById(id);
		log.info("Usuário deletado com sucesso.");
	}
	
	public List<SaidaDTO> listarTodos(){
		return repository.findAll()
				.stream()
				.map(mapper::entidadeParaDto)
				.toList();
	}
	
	public String criarToken(LoginDTO login) {
		Usuario usuario = repository.findByEmail(login.email()).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
		if(!encoder.matches(login.senha(), usuario.getSenha())) throw new SenhaIncorretaException();
	
		log.info("Token de: ", usuario.getNome()," gerado com sucesso.");

		return jwt.gerarToken(usuario.getNome());
	}
	
	private Usuario buscarPorId(Long id) {
		return repository.findById(id).orElseThrow(
				()-> new UsuarioNaoEncontradoException());
	}
}
