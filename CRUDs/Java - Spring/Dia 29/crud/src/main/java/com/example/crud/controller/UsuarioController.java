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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.DTO.EntradaComIdDTO;
import com.example.crud.DTO.EntradaDTO;
import com.example.crud.DTO.LoginDTO;
import com.example.crud.DTO.RetornoDTO;
import com.example.crud.business.UsuarioService;
import com.example.crud.controller.assembler.Assembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Tag(name = "Api CRUD")
public class UsuarioController {

	private final UsuarioService service;
	private final Assembler assembler;

	@Operation(summary = "Adicionar usuario", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario adicionado com sucesso."),
			@ApiResponse(responseCode = "500", description = "Erro ao adicionar usuario")
	})
	@PostMapping
	public ResponseEntity<RetornoDTO> adicioanarUsuario(@RequestBody EntradaDTO entrada){
		RetornoDTO dto = service.adicionarUsuario(entrada);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.adicionarHateoas(dto));
		
	}
	
	@Operation(summary = "Buscar usuario", method = "GET")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario encontrado"),
			@ApiResponse(responseCode = "500", description = "Erro ao buscar usuario")
	})
	@GetMapping(value = "{id}")
	public ResponseEntity<RetornoDTO> buscarUsuario(@PathVariable Long id){
		RetornoDTO dto = service.buscarUsuario(id);
		
		return ResponseEntity.ok(assembler.adicionarHateoas(dto));
	}
	
	@Operation(summary = "Atualizar usuario", method = "PUT")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao atualizar usuario")
	})
	@PutMapping
	public ResponseEntity<RetornoDTO> atualizarUsuario(@RequestBody EntradaComIdDTO entrada ){
		RetornoDTO dto = service.atualizarUsuario(entrada);
		
		return ResponseEntity.ok(assembler.adicionarHateoas(dto));
	}
	
	@Operation(summary = "Deletar usuario", method = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao deletar usuario")
	})
	@DeleteMapping
	public ResponseEntity<Void> deletarUsuario(@RequestParam Long id){
		service.deletarUsuario(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Fazer teste de login", method = "POST")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Teste de login efetuado com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao tentar fazer teste de login")
	})
	@PostMapping(value = "/login")
	public ResponseEntity<RetornoDTO> login(@RequestBody LoginDTO entrada){
		RetornoDTO dto = service.login(entrada);
		
		return ResponseEntity.ok(assembler.adicionarHateoas(dto));
	}
	
	@Operation(summary = "Listar todos", method = "GET")
	@ApiResponses(value= {
			@ApiResponse(responseCode = "200", description= "Usuarios listados com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao listar usuarios")
	})
	@GetMapping
	public List<RetornoDTO> listarTodos(){
		return service.listarTodos();
	}
}
