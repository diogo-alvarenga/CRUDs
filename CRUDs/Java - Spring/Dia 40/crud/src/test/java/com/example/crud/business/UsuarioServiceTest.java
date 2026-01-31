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
	private Mapper mapper;
	
	@Mock
	private PasswordEncoder encoder;
	
	Usuario usuarioBanco = Usuario.builder()
			.nome("nome")
			.email("teste@teste.com")
			.id(1L)
			.senha("senhaCrp")
			.build();
	
	Usuario usuario = Usuario.builder()
			.nome("nome")
			.email("teste@teste.com")
			.senha("123")
			.build();
	
	EntradaDTO entrada = EntradaDTO.builder()
			.nome("nome")
			.email("teste@teste.com")
			.senha("123").build();
	
	SaidaDTO saida = SaidaDTO.builder()
			.nome("nome")
			.email("teste@teste.com")
			.id(1L).build();
	
	AtualizacaoDTO atl = AtualizacaoDTO.builder()
			.nome("novonome")
			.email("novoemail@.com")
			.senha("novasenha")
			.build();
	
	Usuario usuarioAtl = Usuario.builder()
			.nome("novonome")
			.email("novoemail@.com")
			.senha("novasenha")
			.build();
	
	SaidaDTO saidaAtl = SaidaDTO.builder()
			.nome("novonome")
			.email("novoemail@.com")
			.id(1L)
			.build();
	
	LoginDTO login = LoginDTO.builder()
			.email("teste@teste.com")
			.senha("123")
			.build();
	
	@Test
	@DisplayName("Adicionar usuario")
	void adicionarUsuario() {
		
		when(repository.findByEmail(entrada.getEmail())).thenReturn(Optional.empty());
		when(mapper.dtoParaEntidade(entrada)).thenReturn(usuario);
		when(encoder.encode(entrada.getSenha())).thenReturn(usuarioBanco.getSenha());
		when(repository.saveAndFlush(any(Usuario.class))).thenReturn(usuarioBanco);
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
	
		SaidaDTO teste = service.adicionarUsuario(entrada);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@teste.com",teste.getEmail());
	}
	
	@Test
	@DisplayName("Buscar usuario")
	void buscarUsuario() {
		
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO teste = service.buscarUsuario(1L);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@teste.com",teste.getEmail());
	}
	
	@Test
	@DisplayName("Atualizar usuario")
	void atualizarUsuario() {
		
		when(repository.findById(1L)).thenReturn(Optional.of(usuarioBanco));
		when(mapper.atualizacao(atl, usuarioBanco)).thenReturn(usuarioAtl);
		when(encoder.encode(atl.getSenha())).thenReturn(usuarioBanco.getSenha());
		when(repository.saveAndFlush(any(Usuario.class))).thenReturn(usuarioAtl);
		when(mapper.entidadeParaDTO(usuarioAtl)).thenReturn(saidaAtl);
		
		SaidaDTO teste = service.atualizarUsuario(1L, atl);
		
		assertEquals(1L, teste.getId());
		assertEquals("novonome", teste.getNome());
		assertEquals("novoemail@.com",teste.getEmail());
	}
	
	@Test
	@DisplayName("Teste de login")
	void login() {
		when(repository.findByEmail(login.getEmail())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(login.getSenha(), usuarioBanco.getSenha())).thenReturn(true);
		when(mapper.entidadeParaDTO(usuarioBanco)).thenReturn(saida);
		
		SaidaDTO teste = service.login(login);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("teste@teste.com",teste.getEmail());
	}
	
	@Test
	@DisplayName("Email ja utilizado")
	void emailJaUsado() {
	
		when(repository.findByEmail(entrada.getEmail())).thenReturn(Optional.of(usuarioBanco));
		
		assertThrows(EmailExistenteException.class, 
				() -> service.adicionarUsuario(entrada));
	}
	
	@Test
	@DisplayName("Usuario não encontrado")
	void usuarioNaoEncontrado() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(UsuarioNaoEncontradoException.class, 
				() -> service.buscarUsuario(1L));
	}
	
	@Test
	@DisplayName("Senha incorreta")
	void senhaIncorreta() {
		when(repository.findByEmail(login.getEmail())).thenReturn(Optional.of(usuarioBanco));
		when(encoder.matches(login.getSenha(), usuarioBanco.getSenha())).thenReturn(false);
		
		assertThrows(SenhaIncorretaException.class, 
				() -> service.login(login));
	}
}
