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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "CRUD API")
public class UsuarioController {

	private final UsuarioService service;
	private final Assembler assembler;
	
	@Operation(summary = "Adicionar usuario", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario adicionado"),
			@ApiResponse(responseCode = "500", description = "Erro ao adicionar usuario")
	})
	@PostMapping
	public ResponseEntity<SaidaDTO> adicionarUsuario(@Valid @RequestBody EntradaDTO entrada){
		SaidaDTO saida = service.adicionarUsuario(entrada);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.addHateoas(saida));
	}
	
	@Operation(summary = "Buscar usuario", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Buscar usuario"),
			@ApiResponse(responseCode = "500", description = "Erro ao buscar usuario")
	})
	@GetMapping("/{id}")
	public ResponseEntity<SaidaDTO> buscarUsuario(@PathVariable Long id){
		SaidaDTO saida = service.buscarUsuario(id);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@Operation(summary = "Deletar usuario", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuario deletado"),
			@ApiResponse(responseCode = "500", description = "Erro ao deletar usuario")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
		service.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Atualizar usuario", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario atualizado"),
			@ApiResponse(responseCode = "500", description = "Erro ao atualizar usuario")
	})
	@PutMapping("/{id}")
	public ResponseEntity<SaidaDTO> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizacaoDTO atl){
		SaidaDTO saida = service.atualizarUsuario(id, atl);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@Operation(summary = "Listar usuarios", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuarios listados"),
			@ApiResponse(responseCode = "500", description = "Erro ao listar usuarios")
	})
	@GetMapping
	public ResponseEntity<List<SaidaDTO>> listarUsuarios(){
		return ResponseEntity.ok(service.listarUsuarios());
	}
	
	@Operation(summary = "Teste de login", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Teste de login efetuado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao efetuar teste de login")
	})
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDTO login){
		String token = service.login(login);
		
		return ResponseEntity.ok(token);
	}
}
