package com.example.crud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.assembler.Assembler;
import com.example.crud.business.UsuarioService;
import com.example.crud.dto.AtualizacaoDTO;
import com.example.crud.dto.EntradaDTO;
import com.example.crud.dto.LoginDTO;
import com.example.crud.dto.SaidaDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioService service;
	private final Assembler assembler;
	
	@PostMapping
	public ResponseEntity<SaidaDTO> adicionarUsuario(@Valid @RequestBody EntradaDTO entrada){
		SaidaDTO saida = service.adicionarUsuario(entrada);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.addHateoas(saida));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SaidaDTO> buscarUsuario(@PathVariable Long id){
		SaidaDTO saida = service.buscarUsuario(id);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SaidaDTO> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizacaoDTO atl){
		SaidaDTO saida = service.atualizarUsuario(id, atl);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
		service.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<SaidaDTO>> listarTodos(){
		return ResponseEntity.ok(service.listarTodos());
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> gerarToken(@Valid @RequestBody LoginDTO login){
		return ResponseEntity.ok(service.loginGerarToken(login));
	}
	
}
