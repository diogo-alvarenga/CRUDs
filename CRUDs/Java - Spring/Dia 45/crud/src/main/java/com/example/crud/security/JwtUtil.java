package com.example.crud.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String SECRET = "chave-secreta-1234567891011";
	private final long EXPIRATION = 1000* 60*60;
	private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String gerarToken(String nome) {
		// Inicia o builder do JWT (objeto usado para montar o token)
		return Jwts.builder()
				// Define o "subject" do token (normalmente o usuário, login ou nome) é a informação que será retornada no final
				.setSubject(nome)
				
				// Define a data/hora em que o token foi criado
				.setIssuedAt(new Date())
				
				// Define a data/hora de expiração do token
				// System.currentTimeMillis() pega o tempo atual em milissegundos
				// EXPIRATION é o tempo de validade do token
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				
				// Assina o token usando a chave secreta (KEY), ela é um hash da key
				.signWith(KEY)
				
				// Gera o token final em formato String
				.compact();
	}

	public String extrairUsuario(String token) {
		// Cria um parser para ler e validar o token JWT
		return Jwts.parserBuilder()
				// Define a chave usada para validar a assinatura do token
				.setSigningKey(KEY)
				
				// Constrói o parser
				.build()
				
				// Faz o parse do token e valida assinatura e expiração
				.parseClaimsJws(token)
				
				// Obtém o corpo (claims) do token
				.getBody()
				
				// Retorna o "subject", que é o usuário/nome salvo no token
				.getSubject();
	}
}
