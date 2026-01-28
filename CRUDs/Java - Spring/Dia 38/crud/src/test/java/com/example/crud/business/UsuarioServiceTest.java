package com.example.crud.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.crud.DTO.AtualizacaoDTO;
import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.exception.EmailJaCadastradoException;
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
	private PasswordEncoder encoder;
	
	@Mock
	private Mapper mapper;
	
	@Mock
	private UsuarioRepository repository;
	
	@Test
	@DisplayName("Adicionar usuario")
	void adicionarUsuario() {
		EntradaDTO entrada = EntradaDTO.builder()
				.nome("nome")
				.email("email@email.com")
				.senha("senha")
				.build();
		Usuario novoUsuario = Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.senha("senha")
				.build();
		Usuario usuarioCrip = Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.senha("senhaCrip")
				.build();
		Usuario usuarioSalvo = Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.senha("senhaCrip")
				.build();
		SaidaDTO saida = SaidaDTO.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.build();
		
		when(mapper.dtoParaEntidade(entrada)).thenReturn(novoUsuario);
		when(encoder.encode(entrada.getSenha())).thenReturn(usuarioCrip.getSenha());
		when(repository.saveAndFlush(any(Usuario.class))).thenReturn(usuarioSalvo);
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO teste = service.adicionarUsuario(entrada);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("email@email.com", teste.getEmail());	
	}
	
	@Test
	@DisplayName("Buscar usuario")
	void buscarUsuario() {
		
		Optional<Usuario> usuario = Optional.of(
				Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.senha("senhaCrip")
				.build());
		
		Usuario usuarioRecebido = Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.senha("senhaCrip")
				.build();
				
		SaidaDTO saida = SaidaDTO.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.build();
		
		when(repository.findById(1L)).thenReturn(usuario);
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO teste = service.buscarUsuario(1L);
		
		assertEquals(1L, teste.getId());
		assertEquals("nome", teste.getNome());
		assertEquals("email@email.com", teste.getEmail());
	}
	
	@Test
	@DisplayName("Atualizar usuario")
	void atualizarUsuario() {
		AtualizacaoDTO atualizacao = AtualizacaoDTO.builder()
				.nome("novonome")
				.email("novoemail@email.com")
				.senha("novasenha")
				.build();
		Optional<Usuario> usuarioBanco = Optional.of(
				Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.senha("senhaCrip")
				.build());
		Usuario usuarioRecebido = Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.senha("senhaCrip")
				.build();
		Usuario usuarioAtualizado = Usuario.builder()
				.nome("novonome")
				.email("novoemail@email.com")
				.id(1L)
				.senha("novosenha")
				.build();
		SaidaDTO saida = SaidaDTO.builder()
				.nome("novonome")
				.email("novoemail@email.com")
				.id(1L)
				.build();
		
		when(repository.findById(1L)).thenReturn(usuarioBanco);
		when(encoder.encode(atualizacao.getSenha())).thenReturn(usuarioAtualizado.getSenha());
		when(repository.saveAndFlush(any(Usuario.class))).thenReturn(usuarioAtualizado);
		when(mapper.entidadeParaDTO(any(Usuario.class))).thenReturn(saida);
		
		SaidaDTO teste = service.atualizarUsuario(atualizacao, 1L);
		
		assertEquals(1L, teste.getId());
		assertEquals("novonome", teste.getNome());
		assertEquals("novoemail@email.com", teste.getEmail());
	}
	
	@Test
	@DisplayName("Usuario não encontrado")
	void usuarioNaoEncontrado() {
		when(repository.findById(999L)).thenReturn(Optional.empty());
		
		assertThrows(UsuarioNaoEncontradoException.class,
				()->  service.buscarUsuario(999L));
	}
	
	@Test
	@DisplayName("Email ja cadastrado")
	void emailJaCadastrado() {
		EntradaDTO entrada = EntradaDTO.builder()
				.nome("nome")
				.email("email@email.com")
				.senha("senha")
				.build();
		
		Optional<Usuario> usuarioCadastrado = Optional.of(
				Usuario.builder()
				.nome("nome")
				.email("email@email.com")
				.id(1L)
				.senha("senha")
				.build()
				);
		
		when(repository.findByEmail(entrada.getEmail())).thenReturn(usuarioCadastrado);
		
		assertThrows(EmailJaCadastradoException.class,
				()-> service.adicionarUsuario(entrada));
	}
	
	@Test
	@DisplayName("Senha incorreta")
	void senhaIncorreta() {
		
		LoginDTO login = LoginDTO.builder()
				.email("teste@gmail.com")
				.senha("123")
				.build();
		
		Usuario usuario = Usuario.builder()
				.id(1L)
				.nome("nome")
				.email("teste@gmail.com")
				.senha("senhaCryp")
				.build();
		
		when(repository.findByEmail("teste@gmail.com")).thenReturn(Optional.of(usuario));
		when(encoder.matches(login.getSenha(), usuario.getSenha())).thenReturn(false);
		assertThrows(SenhaIncorretaException.class, 
				()-> service.testeLogin(login));
	}

}
