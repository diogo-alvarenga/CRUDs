package com.example.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.business.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/private")
@RequiredArgsConstructor
public class PrivateController {

	private final UsuarioService service;
	
	@GetMapping
	public ResponseEntity<?> acessar(@RequestHeader("Authorization") String authHeader){
		return ResponseEntity.ok(service.acessar(authHeader));
	}
}
