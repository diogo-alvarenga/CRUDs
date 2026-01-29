package com.example.crud.business;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.LoginDTO;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.exception.EmailExistenteException;
import com.example.crud.exception.SenhaIncorretaException;
import com.example.crud.exception.UsuarioNaoEncontradoException;
import com.example.crud.infrastructure.entity.Usuario;
import com.example.crud.infrastructure.repository.UsuarioRepository;
import com.example.crud.mapper.Mapper;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService service;
	
	@Mock
	private UsuarioRepository repository;
	
	@Mock
	private PasswordEncoder encoder;
	
	@Mock
	private Mapper mapper;
	
	@Test
	@DisplayName("Adicionar usuario")
	void adicionarUsuario() {
		EntradaDTO entrada = EntradaDTO.builder()
				.nome("nome")
				.email("teste@email.com")
				.senha("senha")
				.build();
		Usuario usuario = Usuario.builder()
				.nome("nome")
				.email("teste@email.com")
				.senha("senha")
				.build();
		Usuario usuarioBanco = Usuario.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.senha("senhaCryp")
				.build();
		SaidaDTO saida = SaidaDTO.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.build();
		
		when(mapper.dtoParaEntidade(entrada)).thenReturn(usuario);
		when(encoder.encode("senha")).thenReturn(usuarioBanco.getSenha());
		when(repository.saveAndFlush(any(Usuario.class))).thenReturn(usuario);
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO teste = service.adicionarUsurio(entrada);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@email.com", teste.getEmail());
	}
	
	@Test
	@DisplayName("Buscar usuario")
	void  buscarUsuario() {
		Usuario usuarioBanco = Usuario.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.senha("senhaCryp")
				.build();
		SaidaDTO saida = SaidaDTO.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.build();
		
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.entidadeParaDTO(usuarioBanco)).thenReturn(saida);
		
		SaidaDTO teste = service.buscarUsuario(1L);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@email.com", teste.getEmail());
	}
	
	@Test
	@DisplayName("Atualizar usuario")
	void atualizarUsuario() {
		AtualizacaoDTO atl = AtualizacaoDTO.builder()
				.nome("novonome")
				.email("novoemail@.com")
				.senha("novasenha")
				.build();
		Usuario usuarioBanco = Usuario.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.senha("senhaCryp")
				.build();
		Usuario usuarioAtl = Usuario.builder()
				.id(1L)
				.nome("novonome")
				.email("novoemail@.com")
				.senha("novasenha")
				.build();
		SaidaDTO saida = SaidaDTO.builder()
				.id(1L)
				.nome("novonome")
				.email("novoemail@.com")
				.build();
		
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.atualizar(usuarioBanco, atl)).thenReturn(usuarioAtl);
		when(encoder.encode(atl.getSenha())).thenReturn(usuarioAtl.getSenha());
		when(repository.saveAndFlush(any(Usuario.class))).thenReturn(usuarioAtl);
		when(mapper.entidadeParaDTO(usuarioAtl)).thenReturn(saida);
		
		SaidaDTO teste = service.atualizarUsuario( 1L, atl);
		
		assertEquals(1L, teste.getId());
		assertEquals("novonome", teste.getNome());
		assertEquals("novoemail@.com", teste.getEmail());
	}
	
	@Test
	@DisplayName("Teste de login")
	void login() {
		LoginDTO dto = LoginDTO.builder()
				.email("teste@teste.com")
				.senha("senha")
				.build();
		Usuario usuarioBanco = Usuario.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.senha("senhaCryp")
				.build();
		SaidaDTO saida = SaidaDTO.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.build();
		when(repository.findByEmail(dto.getEmail())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(dto.getSenha(), usuarioBanco.getSenha())).thenReturn(true);
		when(mapper.entidadeParaDTO(usuarioBanco)).thenReturn(saida);
		
		SaidaDTO teste = service.login(dto);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@email.com", teste.getEmail());
	}
	
	@Test
	@DisplayName("Email ja existente")
	void emailJaExistente() {
		EntradaDTO entrada = EntradaDTO.builder()
				.nome("nome")
				.email("teste@email.com")
				.senha("senha")
				.build();
		Usuario usuario = Usuario.builder()
				.id(1L)
				.nome("nome")
				.email("teste@email.com")
				.senha("senha")
				.build();
		
		when(repository.existsByEmail(entrada.getEmail())).thenReturn(Optional.of(usuario));
		
		
		assertThrows(EmailExistenteException.class, 
				()-> service.adicionarUsurio(entrada));
	}
	
	@Test
	@DisplayName("Senha incorreta")
	void senhaIncorreta() {
		LoginDTO login = LoginDTO.builder()
				.email("teste@teste.com")
				.senha("123")
				.build();
		Usuario usuario =  Usuario.builder()
		.email("teste@teste.com")
		.senha("321")
		.build();
		
		when(repository.findByEmail(login.getEmail())).thenReturn(Optional.of(usuario));
		when(encoder.matches(login.getSenha(), usuario.getSenha())).thenReturn(false);
		
		assertThrows(SenhaIncorretaException.class,
				()-> service.login(login));
	}
	
	@Test
	@DisplayName("Usuario nao encontrado")
	void usuarioNaoEncontrado() {
		
		when(repository.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(UsuarioNaoEncontradoException.class,
				()-> service.buscarUsuario(999L));
	}
}
