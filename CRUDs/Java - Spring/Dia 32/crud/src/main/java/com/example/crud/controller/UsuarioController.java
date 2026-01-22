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

import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.EntradaIDDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.business.UsuarioService;
import com.example.crud.controller.assembler.Assembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Tag(name = "API CRUD")
public class UsuarioController {

	private final UsuarioService service;
	private final Assembler assembler;
	
	
	@Operation(summary = "Adicionar usuario", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario adicionado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao adicionar usuario")
	})
	@PostMapping
	public ResponseEntity<SaidaDTO> adicionarUsuario(@Valid @RequestBody EntradaDTO entrada){
		SaidaDTO saida = service.adicionarUsuario(entrada);
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.adicionarHateoas(saida));
	}
	
	@Operation(summary = "Buscar usuario", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao buscar usuario")
	})
	@GetMapping(value ="/{id}")
	public ResponseEntity<SaidaDTO> buscarUsuario(@PathVariable Long id){
		SaidaDTO saida = service.buscarUsuario(id);
		
		return ResponseEntity.ok(assembler.adicionarHateoas(saida));
	}
	
	@Operation(summary = "Deletar usuario", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao adicionar usuario")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deletarUsuario(@PathVariable Long id){
		return ResponseEntity.ok().body(service.deletarUsuario(id));
	}
	
	@Operation(summary = "Atualizar usuario", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao atualizar usuario")
	})
	@PutMapping
	public ResponseEntity<SaidaDTO> atualizarUsuario(@Valid @RequestBody EntradaIDDTO entrada){
		SaidaDTO saida = service.atualizarUsuario(entrada);
		
		return ResponseEntity.ok(assembler.adicionarHateoas(saida));
	}
	
	@Operation(summary = "Teste de Login", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Teste de login efetuado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao realizar teste de login")
	})
	@PostMapping(value = "/login")
	public ResponseEntity<SaidaDTO> login(@Valid @RequestBody LoginDTO login){
		SaidaDTO saida = service.login(login);
		
		return ResponseEntity.ok(assembler.adicionarHateoas(saida));
	}
	
	@Operation(summary = "Listar todos os usuarios", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listar usuarios"),
			@ApiResponse(responseCode = "500", description = "Erro ao listar usuarios")
	})
	@GetMapping
	public List<SaidaDTO> listarUsuarios(){
		return service.listarTodos();
	}
}
