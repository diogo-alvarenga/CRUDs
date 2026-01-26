package com.example.crud.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.infrastructure.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService service;
	
	@Mock
	private UsuarioRepository repository;
	
	
	@Test
	void retornarUsuario() {
		
		when(service.buscarUsuario(1L)).thenReturn(new SaidaDTO());

		EntradaDTO entradaTeste = new EntradaDTO("Usuario", "usuario@gmail.com", "12345");
		SaidaDTO saidaTeste = service.adicionarUsuario(entradaTeste);

		
		service.buscarUsuario(1L);
		
		fail("Not yet implemented");
	}

}
