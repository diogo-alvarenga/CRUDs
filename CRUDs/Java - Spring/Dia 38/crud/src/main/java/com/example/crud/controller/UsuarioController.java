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

import com.example.crud.DTO.AtualizacaoDTO;
import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.SaidaDTO;
import com.example.crud.assembler.Assembler;
import com.example.crud.business.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	private final Assembler assembler;
	
	@Operation(summary = "Adicionar Usuario", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario criado"),
			@ApiResponse(responseCode = "400", description = "Erro de validação"),
			@ApiResponse(responseCode = "409", description = "Usuario já existe")
	})
	@PostMapping
	public ResponseEntity<SaidaDTO> adicionarUsuario(@Valid @RequestBody EntradaDTO entrada){
		SaidaDTO saida = service.adicionarUsuario(entrada);

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
	
	@Operation(summary = "Atualizar usuario", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario atualizado"),
			@ApiResponse(responseCode = "400", description = "Dados invalidos"),
			@ApiResponse(responseCode = "404", description = "Usuario não encontrado")
	})
	@PutMapping("/{id}")
	public ResponseEntity<SaidaDTO> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizacaoDTO entrada){
		SaidaDTO saida = service.atualizarUsuario(entrada, id);
		
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
	
	@Operation(summary = "Listar usuarios", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuarios listados")
	})
	@GetMapping
	public List<SaidaDTO> listarTodos(){
		return service.listarTodos();
	}
	
	@Operation(summary = "Teste de login de usuario", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Teste de login de usuario concluido"),
			@ApiResponse(responseCode = "400", description = "Dados invalidos"),
			@ApiResponse(responseCode = "401", description = "Senha incorreta")

	})
	@PostMapping("/login")
	public ResponseEntity<SaidaDTO> testeLogin(@Valid @RequestBody LoginDTO login){
		SaidaDTO saida = service.testeLogin(login);
		
		return ResponseEntity.ok(saida);
	}
	
}
