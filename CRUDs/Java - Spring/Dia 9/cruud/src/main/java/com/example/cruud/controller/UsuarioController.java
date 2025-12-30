package com.example.cruud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cruud.business.UsuarioService;
import com.example.cruud.infrastructure.entitys.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	
	@PostMapping
	public ResponseEntity<Usuario> adicionarUsuario(@RequestBody Usuario usuario){
		
		return ResponseEntity.ok(service.salvarUsuario(usuario));
	}
	
	@GetMapping
	public ResponseEntity<Usuario> buscarUsuario(@RequestParam Integer id){
		return ResponseEntity.ok(service.buscarUsuario(id));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuario(@RequestParam Integer id){
		service.deletarUsuario(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(params = "id")
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario,@RequestParam Integer id){
		return ResponseEntity.ok(service.atualizarUsuario(usuario, id));
	}
	
	@PutMapping(params = "email")
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario,@RequestParam String email){
		return ResponseEntity.ok(service.atualizarUsuarioPorEmail(usuario, email));
	}
	
}


