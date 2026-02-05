package com.example.crud.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.crud.exception.UsuarioNaoAutorizadoException;
import com.example.crud.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
class PrivateServiceTest {

	@InjectMocks
	private PrivateService service;
	
	@Mock
	private JwtUtil jwt;
	
	String authHeader = "a";
	
	@Test
	void acessoNegado() {
		when(authHeader.startsWith("Bearer ")).thenReturn(false);
		
		assertThrows(UsuarioNaoAutorizadoException.class,
				() -> service.acessar(authHeader));
	}

}
