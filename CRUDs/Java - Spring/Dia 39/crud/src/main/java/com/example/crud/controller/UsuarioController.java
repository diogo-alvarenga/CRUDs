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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

	private final UsuarioService service;
	private final Assembler assembler;
	
	@Operation(summary = "Adicionar usuario", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario criado"),
			@ApiResponse(responseCode = "400", description = "Erro de validação"),
			@ApiResponse(responseCode = "409", description = "Usuario já existe")

	})
	@PostMapping
	public ResponseEntity<SaidaDTO> adicionarUsuario(@Valid @RequestBody EntradaDTO entrada){
		SaidaDTO saida = service.adicionarUsurio(entrada);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.addHateoas(saida));
	}
	
	@Operation(summary = "Buscar usuario", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado"),
			@ApiResponse(responseCode = "404", description = "Usuario não encontrado")
	})
	@GetMapping("/{id}")
	public ResponseEntity<SaidaDTO> buscarUsuario(@PathVariable Long id){
		SaidaDTO saida = service.buscarUsuario(id);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@Operation(summary = "Deletar usuario", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuario deletado"),
			@ApiResponse(responseCode = "404", description = "Usuario não encontrado")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
		service.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Atualizar usuario", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario atualizado"),
			@ApiResponse(responseCode = "400", description = "Dados invalidos"),
			@ApiResponse(responseCode = "404", description = "Usuario não encontrado")
	})
	@PutMapping("/{id}")
	public ResponseEntity<SaidaDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody AtualizacaoDTO atz) {
		SaidaDTO saida = service.atualizarUsuario(id, atz);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@Operation(summary = "Listar todos", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuarios listados")
	})
	@GetMapping
	public List<SaidaDTO> listarTodos(){
		return service.listarUsuarios();
	}
	
	@Operation(summary = "Teste de login", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Teste de login"),
			@ApiResponse(responseCode = "400", description = "Dados invalidos"),
			@ApiResponse(responseCode = "401", description = "Senha incorreta")

	})
	@PostMapping("/login")
	public ResponseEntity<SaidaDTO> testeLogin(@Valid @RequestBody LoginDTO login){
		SaidaDTO saida = service.login(login);
		
		return ResponseEntity.ok(saida);
	}
}
