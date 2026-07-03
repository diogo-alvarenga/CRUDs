package com.example.crud.controller;

import com.example.crud.business.UsuarioService;
import com.example.crud.dto.UsuarioAtualizacaoDTO;
import com.example.crud.dto.UsuarioEntradaDTO;
import com.example.crud.dto.UsuarioSaidaDTO;
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
    public ResponseEntity<UsuarioSaidaDTO> adicionarUsuario(@Valid @RequestBody UsuarioEntradaDTO entrada){
        UsuarioSaidaDTO saida = service.adicionarUsuario(entrada);

        return ResponseEntity.status(HttpStatus.CREATED).body(saida);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSaidaDTO> atualizarUsuario(@Valid @RequestBody UsuarioAtualizacaoDTO atl, @PathVariable Long id){
        UsuarioSaidaDTO saida = service.atualizarUsuario(atl, id);

        return ResponseEntity.ok(saida);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        service.deletarUsuarioPorId(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSaidaDTO> buscarUsuarioPorId(@PathVariable Long id){
        UsuarioSaidaDTO saida = service.buscarUsuario(id);

        return ResponseEntity.ok(saida);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioSaidaDTO>> listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }


}
