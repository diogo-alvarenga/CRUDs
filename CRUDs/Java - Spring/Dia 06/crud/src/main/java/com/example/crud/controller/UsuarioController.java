package com.example.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.business.UsuarioService;
import com.example.crud.infrastructure.entitys.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

	private final UsuarioService service;
	
 
	@PostMapping
	public ResponseEntity<Void> salvarUsuario(@RequestBody Usuario usuario){
		service.salvarUsuario(usuario);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<Usuario>buscarUsuarioPorEmail(@RequestParam String email){
		
		return ResponseEntity.ok(service.buscarUsuarioPorEmail(email));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuarioPoId(@RequestParam String email){
		service.deletarUsuarioPorEmail(email);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Usuario> atualizarUsuarioPorId(@RequestParam Integer id, @RequestBody Usuario usuario){
		return ResponseEntity.ok(service.atualizarUsuarioPorId(id, usuario));
	}
	
}
