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
import com.example.crud.dto.LoginSaida;
import com.example.crud.dto.SaidaDTO;
import com.example.crud.jwtutil.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioService service;
	private final Assembler assembler;
	private final JwtUtil jwtUtil;
	@PostMapping
	public ResponseEntity<SaidaDTO> adicionarUsuarios(@Valid @RequestBody EntradaDTO entrada ){
		SaidaDTO saida = service.adicionarUsuario(entrada);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(assembler.addHateoas(saida));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SaidaDTO> buscarUsuario(@PathVariable Long id){
		SaidaDTO saida = service.buscarUsuario(id);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<SaidaDTO> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody AtualizacaoDTO atl){
		SaidaDTO saida = service.atualizarUsuario(id, atl);
		
		return ResponseEntity.ok(assembler.addHateoas(saida));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
		service.deletarPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginSaida> login(@Valid @RequestBody LoginDTO login){
		SaidaDTO saida = service.login(login);
		String token = jwtUtil.generateToken(saida.getNome());
		return ResponseEntity.ok(new LoginSaida(token));
	}
	
	@GetMapping
	public List<SaidaDTO> listarUsuarios(){
		return service.listarTodos();
	}
}
