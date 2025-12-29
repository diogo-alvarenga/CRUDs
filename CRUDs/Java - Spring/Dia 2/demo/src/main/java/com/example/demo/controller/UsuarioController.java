package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.business.UsuarioService;
import com.example.demo.structure.entity.Usuario;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	
	@PostMapping
	public ResponseEntity<Usuario> adicionarUsuario(@RequestBody Usuario usuario){
		return ResponseEntity.ok(service.adicionarUsuario(usuario));
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuario(@RequestParam Integer id){
		service.deletarUsuarioPorId(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario,@RequestParam Integer id){
		return ResponseEntity.ok(service.atualizarUsuarioPorId(usuario, id));
	}
	
	@GetMapping
	public ResponseEntity<Usuario> buscarUsuario(@RequestParam Integer id){
		return ResponseEntity.ok(service.buscarUsuarioPorId(id));
	}
}
