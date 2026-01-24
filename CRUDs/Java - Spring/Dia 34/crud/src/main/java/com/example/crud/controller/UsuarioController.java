package com.example.crud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.DTO.AtualizacaoDTO;
import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.assembler.Assembler;
import com.example.crud.business.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	private final Assembler assembler;
	
	@PostMapping
	public ResponseEntity<SaidaDTO> adicionarUsuario(@Valid @RequestBody EntradaDTO entrada){
		SaidaDTO saida = service.adicionarUsuario(entrada);
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.adicionarHateoas(saida));
	}
	
	@GetMapping(value ="/{id}")
	public ResponseEntity<SaidaDTO> buscarUsuario(@PathVariable Long id){
		SaidaDTO saida = service.buscarUsuario(id);
		return ResponseEntity.ok(assembler.adicionarHateoas(saida));
	}
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<SaidaDTO> deletarUsuario(@PathVariable Long id){
		service.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<SaidaDTO> atualizarUsuario( @PathVariable Long id, @RequestBody AtualizacaoDTO entrada){
		SaidaDTO saida = service.atualizarUsuario(entrada, id);
		return ResponseEntity.ok(assembler.adicionarHateoas(saida));
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<SaidaDTO> login(@Valid @RequestBody LoginDTO login){
		SaidaDTO saida = service.login(login);
		return ResponseEntity.ok(saida);
	}
	
	@GetMapping
	public List<SaidaDTO> listar(){
		return service.listarTodos();
	}
}
