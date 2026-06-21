package com.example.crud.controller;

import com.example.crud.business.UsuarioService;
import com.example.crud.dto.AtualizacaoUsuarioDTO;
import com.example.crud.dto.EntradaUsuarioDTO;
import com.example.crud.dto.SaidaUsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<SaidaUsuarioDTO> adicionarUsuario(@Valid @RequestBody EntradaUsuarioDTO usuario){
        SaidaUsuarioDTO saida = service.adicionarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saida);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaidaUsuarioDTO> buscarUsuario(@PathVariable Long id){
        SaidaUsuarioDTO saida = service.buscarUsuario(id);
        return ResponseEntity.ok(saida);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id){
        service.deletarUsuario(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaidaUsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody AtualizacaoUsuarioDTO usuario){
        SaidaUsuarioDTO saida = service.atualizarUsuario(id,usuario);
        return ResponseEntity.ok(saida);
    }

    @GetMapping()
    public ResponseEntity<List<SaidaUsuarioDTO>> listarTodos(){

        return ResponseEntity.ok(service.listarTodos());
    }
}
