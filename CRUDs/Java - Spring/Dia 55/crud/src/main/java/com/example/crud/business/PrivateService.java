package com.example.crud.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.exception.UsuarioNaoAutorizadoException;
import com.example.crud.security.JwtUtil;

@Service
public class PrivateService {

	@Autowired
	private JwtUtil jwt;
	
	public String acessar(String authHeader){
		if(!authHeader.startsWith("Bearer ")) throw new UsuarioNaoAutorizadoException();
		
		String token = authHeader.substring(7);
		String nome = jwt.extrairToken(token);
		
		return "Ola, "+ nome;
	}
}
